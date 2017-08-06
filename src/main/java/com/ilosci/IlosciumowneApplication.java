package com.ilosci;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.org.apache.regexp.internal.RE;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.AliasToBeanConstructorResultTransformer;
import org.hibernate.transform.AliasToBeanResultTransformer;
import org.hibernate.transform.Transformers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

@SuppressWarnings("ALL")
@SpringBootApplication
public class IlosciumowneApplication {

	public static void main(String[] args) {
		SpringApplication.run(IlosciumowneApplication.class, args);
	}

	@Autowired
	Iloscrepo iloscrepo;
	@Autowired
	KatRepo katRepo;
	@Autowired
	JednostkaRepo jednostkaRepo;

	@Autowired
	EntityManagerFactory entityManagerFactory;

	Logger logger = LoggerFactory.getLogger(this.getClass());


	@PostConstruct
	public void dodaj(){

		Session session = entityManagerFactory.createEntityManager().unwrap(Session.class);
		Transaction transaction = session.beginTransaction();

		Kategoria kat1 = new Kategoria("Kat1");
		Kategoria kat2 = new Kategoria("Kat2");
		session.save(kat1);
		session.save(kat2);

		Jednostka jednostka = new Jednostka();
		jednostka.setName("J");
		Jednostka jednostka1 = new Jednostka();
		jednostka1.setName("K");

		IlosciUmowne ilosciUmowne = new IlosciUmowne();
		ilosciUmowne.setName("umowa jeden");

		IlosciUmowne ilosciUmowne1 = new IlosciUmowne();
		ilosciUmowne1.setName("umowa dwa");

		jednostka.setKategoria(kat1);
		jednostka1.setKategoria(kat2);

		ilosciUmowne.setJednostka(new HashSet<>(Arrays.asList(jednostka,jednostka1)));

		ilosciUmowne1.setJednostka(new HashSet<>(Arrays.asList(jednostka1)));

		session.save(jednostka);
		session.save(jednostka1);
		session.save(ilosciUmowne);
		session.save(ilosciUmowne1);

		List<String> list = entityManagerFactory
				.createEntityManager()
				.createQuery("select distinct k.name from IlosciUmowne i join i.jednostka j join j.kategoria k where i.id=:id or i.id=:id2")
				.setParameter("id",1L)
				.setParameter("id2",2L)
				.getResultList();

		Kategoria kat4 = new Kategoria("Kat3");

		session.save(kat4);
		Test test = new Test(kat4.getId());

		session.save(test);



		transaction.commit();
		session.close();

		logger.info("{}" , list);

		Session session1 = entityManagerFactory.createEntityManager().unwrap(Session.class);
		Transaction transaction1 = session1.beginTransaction();

		IlosciUmowne ilosciUmown = session1.load(IlosciUmowne.class,new Long(1));
		IlosciUmowne ilosciUmown1 = session1.get(IlosciUmowne.class,new Long(2));
		IlosciUmowne il = session1.get(IlosciUmowne.class,new Long(2));

		Kategoria kat = session1.load(Kategoria.class , new Long(1));

		logger.info("{}",ilosciUmown instanceof IlosciUmowne);
		logger.info(ilosciUmown1.getClass().toString());

		logger.info(ilosciUmown.getClass().toString());
		transaction1.commit();
		session1.close();
	}

//	@PostConstruct
//	public void save(){
//		Kategoria kat1 = new Kategoria("Kat1");
//		Kategoria kat2 = new Kategoria("Kat2");
//		katRepo.save(kat1);
//		katRepo.save(kat2);
//
//		Jednostka jednostka = new Jednostka();
//		jednostka.setName("J");
//		Jednostka jednostka1 = new Jednostka();
//		jednostka1.setName("K");
//
//		IlosciUmowne ilosciUmowne = new IlosciUmowne();
//		ilosciUmowne.setName("umowa jeden");
//
//		IlosciUmowne ilosciUmowne1 = new IlosciUmowne();
//		ilosciUmowne1.setName("umowa dwa");
//
//		jednostka.setKategoria(kat1);
//		jednostka1.setKategoria(kat2);
//
//		jednostkaRepo.save(jednostka);
//		jednostkaRepo.save(jednostka1);
//
//		ilosciUmowne.setJednostka(new HashSet<>(Arrays.asList(jednostka,jednostka1)));
//
//		ilosciUmowne1.setJednostka(new HashSet<>(Arrays.asList(jednostka1)));
//
//		iloscrepo.save(ilosciUmowne);
//		iloscrepo.save(ilosciUmowne1);
//	}

	@PostConstruct
	public void criteriaPost(){

		Criteria crit = entityManagerFactory.createEntityManager().unwrap(Session.class).createCriteria(IlosciUmowne.class);
		crit.createAlias("jednostka" , "jednostka")
			.createAlias("jednostka.kategoria" , "kategoria")
			.add(Restrictions.eq("jednostka.name", "J"))
			.setProjection(getProjectionList());

		crit.setResultTransformer(new AliasToBeanResultTransformer(GroupDTO.class));

		GroupDTO groupDTO = (GroupDTO) crit.uniqueResult();
		logger.info("{}" , groupDTO);

	}

	public ProjectionList getProjectionList(){

		ProjectionList projectionList = Projections.projectionList();
		projectionList.add(Projections.property("name"), "name")
				.add(Projections.property("jednostka.name"), "jednostkaName")
				.add(Projections.property("kategoria.name"), "kategoriaName");

		return projectionList;
	}

	@PostConstruct
	public void criteriaPost1() throws NoSuchMethodException {

		Criteria crit = entityManagerFactory.createEntityManager().unwrap(Session.class).createCriteria(IlosciUmowne.class);
		crit.createAlias("jednostka" , "jednostka")
//				.createAlias("jednostka.kategoria" , "kategoria")
				.add(Restrictions.eq("id", 1L))
				.setProjection(getProjectionList1());
		crit.setFetchMode("jednostka", FetchMode.JOIN);

//		crit.setResultTransformer(new AliasToBeanConstructorResultTransformer(GroupDTO.class.getConstructor(new Class[]{String.class,Jednostka.class})));
//		GroupDTO groupDTO = (GroupDTO) crit.uniqueResult();
//		logger.info("{}" , groupDTO);

	}

	public ProjectionList getProjectionList1(){

		ProjectionList projectionList = Projections.projectionList();
		projectionList.add(Projections.property("name"), "name")
				.add(Projections.property("jednostka"), "jednostka");
//				.add(Projections.property("jednostka.kategoria"), "kategoria");

		return projectionList;
	}

	@PostConstruct
	public void criteriaPost2() throws NoSuchMethodException {

		Criteria crit = entityManagerFactory.createEntityManager().unwrap(Session.class).createCriteria(Jednostka.class);
				crit.createAlias("kategoria" , "kategoria")
				.add(Restrictions.eq("name", "J"))
				.setProjection(getProjectionList2());

		crit.setResultTransformer(new AliasToBeanConstructorResultTransformer(GroupDTO.class.getConstructor(new Class[]{String.class,Kategoria.class})));
		GroupDTO groupDTO = (GroupDTO) crit.uniqueResult();
		logger.info("{}" , groupDTO);

	}

	public ProjectionList getProjectionList2(){

		ProjectionList projectionList = Projections.projectionList();
		projectionList.add(Projections.property("name"), "name");
		projectionList.add(Projections.property("kategoria"), "kategoria");

		return projectionList;
	}

	@PostConstruct
	public void criteriaPost3() throws NoSuchMethodException {

		Criteria crit = entityManagerFactory.createEntityManager().unwrap(Session.class).createCriteria(Jednostka.class);
		crit.createAlias("kategoria" , "kategoria")
				.add(Restrictions.eq("name", "J"))
				.setProjection(getProjectionList3());


		crit.setResultTransformer(new AliasToBeanConstructorResultTransformer(GroupDTO.class.getConstructor(new Class[]{String.class,String.class})));
		GroupDTO groupDTO = (GroupDTO) crit.uniqueResult();
		logger.info("{}" , groupDTO);

	}

	public ProjectionList getProjectionList3(){

		ProjectionList projectionList = Projections.projectionList();
		projectionList.add(Projections.property("name"), "name");
		projectionList.add(Projections.property("kategoria.name"), "kategoria");

		return projectionList;
	}



}
