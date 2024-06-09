package com.member.model;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.util.ProductUtil;

public class MemberDAO implements MemberDAO_interface {
	
//	 private static final String FIND_ONE_MEMBER = "FROM MemberVO WHERE memberId = :memberId";
//	    private static final String UPDATE_MEMBER_PASSWD = "UPDATE MemberVO SET memberPassword = :password WHERE memberId = :memberId";
//	    private static final String UPDATE_MEMBER_IMG = "UPDATE MemberVO SET memberImg = :image WHERE memberId = :memberId";
//	    private static final String DELETE = "DELETE FROM MemberVO WHERE memberId = :memberId";
//	    private static final String FIND_ONE_REGISTER = "FROM MemberVO WHERE memberAccount = :account AND memberPassword = :password";
//	    private static final String FIND_ALL_MEMBER = "FROM MemberVO ORDER BY memberId";
//	    private static final String CHECK_UK_EMAIL = "SELECT COUNT(*) FROM MemberVO WHERE memberEmail = :email";
//	    private static final String FIND_ALL_ORDER = "FROM ProductOrderVO WHERE memberId = :memberId";

	private SessionFactory factory;
	
	public MemberDAO() {
		factory=ProductUtil.getSessionFactory();	
	}
	

	@Override
	public void insert(MemberVO member) {
		// TODO Auto-generated method stub
		Session session = factory.getCurrentSession();
		session.beginTransaction();
		session.save(member);
		session.getTransaction().commit();		
	}

	@Override
	public void update(MemberVO member) {
		    Session session = factory.getCurrentSession();
		    session.beginTransaction();
	    	session.update(member);
	        session.getTransaction().commit();
	    }
	
	@Override
	public void updateImg(MemberVO member) {
		Session session = factory.getCurrentSession();
		session.beginTransaction();
		String HQLL="UPDATE MemberVO SET memberImg = :image WHERE memberId = :memberId";
		session.createQuery(HQLL).setParameter("image", member.getMemberImg()).setParameter("memberId",member.getMemberId()).executeUpdate();
		session.getTransaction().commit();
	}


	@Override
	public MemberVO findOneRegister(String memberAccount, String memberPaswd) {
		  Session session = factory.getCurrentSession();
		    session.beginTransaction();
		    String HQLL="FROM MemberVO where memberAccount = :account AND memberPassword = :password";
		    MemberVO member = session.createQuery(HQLL,MemberVO.class). setParameter("account", memberAccount)
                    .setParameter("password", memberPaswd).uniqueResult();		    
		    session.getTransaction().commit();
		
		return member;	
		
	}
	
	public boolean findUkEmail(MemberVO member) {
	    boolean emailExists = false;
	    Session session = factory.getCurrentSession(); 
	    session.beginTransaction();
	    String HQLL= "SELECT COUNT(*) FROM MemberVO WHERE memberEmail = :email";
	    Long result = (Long)session.createQuery(HQLL).setParameter("email", member.getMemberEmail()).uniqueResult();
	    int count = result.intValue();
	    emailExists = count > 0;
	    session.getTransaction().commit();
	    return emailExists;   
	}
	
	
	@Override
	public MemberVO findMemByToken(String verifyToken) {
		  Session session = factory.getCurrentSession();
		  session.beginTransaction();
		  String HQLL= "FROM MemberVO WHERE verificationToken = :token";
		  MemberVO member = session.createQuery(HQLL,MemberVO.class). setParameter("token", verifyToken)
                  .uniqueResult();		
		  
		    session.getTransaction().commit();		
		return member;			
	}


	@Override
	public void updateVerify(Integer memberId) {
		 Session session = factory.getCurrentSession();
		  session.beginTransaction();
		  String HQLL="UPDATE MemberVO SET isVerified = true WHERE memberId = :id";
		  session.createQuery(HQLL).setParameter("id", memberId).executeUpdate();	  
		  session.getTransaction().commit();	
	}
	
	
	
	public void update_passwd(String password,Integer memberId) {
		 Session session = factory.getCurrentSession();
		  session.beginTransaction();
		  String HQLL="UPDATE MemberVO SET memberPassword =:newpaswd WHERE memberId = :id";
		  session.createQuery(HQLL).setParameter("newpaswd", password).setParameter("id", memberId) .executeUpdate();	  
		  session.getTransaction().commit();	
	}
	
	public MemberVO findMemByEmail(String memberEmail) {
		Session session = factory.getCurrentSession();
		  session.beginTransaction();
		  String HQLL="FROM MemberVO WHERE memberEmail =:email";
		  MemberVO member= session.createQuery(HQLL,MemberVO.class).setParameter("email", memberEmail).uniqueResult();
		  session.getTransaction().commit();
		  return member;
		  
	}
	
	public MemberVO findByMemId(Integer memberId) {
		Session session = factory.getCurrentSession();
		  session.beginTransaction();
		  MemberVO member = session.get(MemberVO.class, memberId);
		  Hibernate.initialize(member.getProdOrders()); 
		  Hibernate.initialize(member.getTicketOrders()); 
		   session.getTransaction().commit();
		   return member;
	}
	
	
	
	}
	
	
	
	
	
	
	
	
	
	
	

