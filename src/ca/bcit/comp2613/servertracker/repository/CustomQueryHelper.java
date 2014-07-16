package ca.bcit.comp2613.servertracker.repository;

import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import ca.bcit.comp2613.servertracker.model.*;

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

	public Server getServerWithId(String serverId) {
		Server retval = null;
		EntityManager em = null;
		try {
			em = emf.createEntityManager();
			Server server= em.find(Server.class, serverId);
			return server;
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

	public PowerCCT getPowerCCTWithId(String powerCCTId) {
		PowerCCT retval = null;
		EntityManager em = null;
		try {
			em = emf.createEntityManager();
			PowerCCT powerCCT= em.find(PowerCCT.class, powerCCTId);
			return powerCCT;
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
	
	public List<Server> getServersOfCabinet(String cabinetId) {
		List<Server> retval = null;
		EntityManager em = null;
		try {
			em = emf.createEntityManager();
			Cabinet cabinet= em.find(Cabinet.class, cabinetId);
			cabinet.getServersArray().size(); // make a db call
			return cabinet.getServersArray();
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

	public List<PowerCCT> getPowerCCTsOfCabinet(String cabinetId) {
		List<PowerCCT> retval = null;
		EntityManager em = null;
		try {
			em = emf.createEntityManager();
			Cabinet cabinet= em.find(Cabinet.class, cabinetId);
			cabinet.getPowerCCTArray().size(); // make a db call
			return cabinet.getPowerCCTArray();
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
