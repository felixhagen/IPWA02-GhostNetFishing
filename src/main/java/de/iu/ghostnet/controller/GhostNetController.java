package de.iu.ghostnet.controller;

import de.iu.ghostnet.model.GhostNet;
import de.iu.ghostnet.model.GhostNetStatus;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.Serializable;
import java.util.List;

@Named("ghostNetController")
@SessionScoped
public class GhostNetController implements Serializable {

    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("ghostnetPU");

    private GhostNet newNet = new GhostNet();
    private List<GhostNet> cachedNets;

    @PostConstruct
    public void init() {
        loadListFromDb();
    }

    private void loadListFromDb() {
        EntityManager em = emf.createEntityManager();
        try {
            cachedNets = em.createQuery("SELECT g FROM GhostNet g", GhostNet.class).getResultList();
        } finally {
            em.close();
        }
    }

    public List<GhostNet> getAllNets() {
        if (cachedNets == null) {
            loadListFromDb();
        }
        return cachedNets;
    }

    public String saveNet() {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            newNet.setStatus(GhostNetStatus.GEMELDET);
            em.persist(newNet);
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
        } finally {
            em.close();
        }

        newNet = new GhostNet();
        loadListFromDb();
        return "index?faces-redirect=true";
    }

    private void updateStatus(GhostNet net, GhostNetStatus newStatus) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            GhostNet managedNet = em.find(GhostNet.class, net.getId());
            if (managedNet != null) {
                managedNet.setStatus(newStatus);
                if (newStatus == GhostNetStatus.BERGUNG_BEVORSTEHEND) {
                    managedNet.setRescuerName(net.getRescuerName());
                    managedNet.setRescuerPhone(net.getRescuerPhone());
                }
                em.merge(managedNet);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
        } finally {
            em.close();
        }
        loadListFromDb();
    }

    public void statusToRecovering(GhostNet net) {
        updateStatus(net, GhostNetStatus.BERGUNG_BEVORSTEHEND);
    }

    public void statusToRecovered(GhostNet net) {
        updateStatus(net, GhostNetStatus.GEBORGEN);
    }

    public void statusToLost(GhostNet net) {
        updateStatus(net, GhostNetStatus.VERSCHOLLEN);
    }

    public GhostNet getNewNet() { return newNet; }
    public void setNewNet(GhostNet newNet) { this.newNet = newNet; }
}