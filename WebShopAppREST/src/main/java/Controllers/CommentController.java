package Controllers;

import java.util.ArrayList;
import java.util.Collection;

import dao.DAO;
import models.Comment;
import models.Factory;

public class CommentController {
	private String contextPath;
	private DAO commentDao;
	
	public CommentController(String contextPath) {
		this.contextPath = contextPath;
		commentDao = new DAO<Comment>(contextPath, Comment.class);
	}
	
	public Collection<Comment> getAll(){
		return commentDao.GetAll();
	}
	
	public Collection<Comment> getByFactoryId(int id){
		Collection<Comment> comments = new ArrayList<Comment>();
		
		for (Comment comment : getAll()) {
			if (comment.getFactoryId() == id) 
				comments.add(comment);
		}
		
		return comments;
	}
}
