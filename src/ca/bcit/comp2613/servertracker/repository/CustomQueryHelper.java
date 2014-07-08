package ca.bcit.comp2613.servertracker.repository;

import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import ca.bcit.comp2613.servertracker.model.Cabinet;

public class CustomQueryHelper {
	private EntityManagerFactory emf;

	public EntityManagerFactory getEmf() {
		return emf;
	}

	public void setEmf(EntityManagerFactory emf) {
		this.emf = emf;
	}

	public CustomQueryHelper(EntityManagerFactory emf) {
		this.emf = emf;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	public Cabinet getCabinetWithId(String cabinetId) {
		Cabinet retval = null;
		EntityManager em = null;
		try {
			em = emf.createEntityManager();
			Cabinet cabinet= em.find(Cabinet.class, cabinetId);
//			cabinet.getStudents().size(); // make a db call
//			return cabinetId.getStudents();
			return cabinet;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				em.close();
			} catch (Exception e) {
			}
		}
		return retval;
	}
}
