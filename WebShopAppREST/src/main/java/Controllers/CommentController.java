package Controllers;

import java.util.ArrayList;
import java.util.Collection;

import dao.DAO;
import models.Comment;
import models.CommentState;
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
	
	public void Save(Comment comment){
		commentDao.Save(comment);
	}
	
	public Collection<Comment> getByFactoryId(int id){
		Collection<Comment> comments = new ArrayList<Comment>();
		
		for (Comment comment : getAll()) {
			if (comment.getFactoryId() == id) 
				comments.add(comment);
		}
		
		return comments;
	}
	
	public Collection<Comment> GetAcceptedByFactoryId(int factoryId){
		Collection<Comment> comments = new ArrayList<Comment>()	;
		
		for(Comment comment: this.getByFactoryId(factoryId)) {
			if(comment.getState() == CommentState.Accepted) {
				comments.add(comment);
			}
		}
		
		return comments;
	}
	
	public Boolean Update(Comment comment) {
		return commentDao.Update(comment);
	}
	
	public Comment GetById(int commentId) {
		return (Comment) commentDao.GetById(commentId);
	}
	
	public Boolean AcceptOrRejectComment(int commentId, int logic) {
		Comment comment = this.GetById(commentId);
		if(comment == null) {
			return false;
		}
		
		if(comment.getState() != CommentState.Pending) {
			return false;
		}
		
		if(logic == 0) {
			comment.setState(CommentState.Rejected);
		}
		else if(logic == 1){
			comment.setState(CommentState.Accepted);
		}
		else {
			return false;
		}
		
		return this.Update(comment);
	}
	
	public ArrayList<Comment> GetByUserIdAndFactoryId(int userId, int factoryId){
		ArrayList<Comment> comments = new ArrayList<Comment>();
		
		for (Comment comment : getAll()) {
			if (comment.getUserId() == userId && comment.getFactoryId() == factoryId) 
				comments.add(comment);
		}
		
		return comments;
	}
	
	public int GetNumberOfCommentsOnFactory(int userId, int factoryId) {
		return this.GetByUserIdAndFactoryId(userId,factoryId).size();
	}
	
}
