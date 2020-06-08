package Chat;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class ChatDAO {
	
	DataSource ds;
	Connection conn = null;
	ResultSet rs = null;
	PreparedStatement pstmt = null;

	public ChatDAO() {
		
		try {
			
			Context context = new InitialContext();
			ds = (DataSource) context.lookup("java:comp/env/jdbc/orcl");
			
		} catch (Exception e) {
				e.printStackTrace();
		}
	}
	
	private void dbclose() {
		try {
			if(rs != null) {
				rs.close();
			}
			if(pstmt != null) {
				pstmt.close();
			}
			if(conn != null) {
				conn.close();
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public ArrayList<ChatDTO> getChatList(String nowTime){
		
		ArrayList<ChatDTO> chatlist = null;
		String sql = "select * from chat where chatTime > ? order by chatTime";
		try {
			
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, nowTime);
			rs = pstmt.executeQuery();
			chatlist = new ArrayList<ChatDTO>();
			
			while(rs.next()) {
				ChatDTO chat = new ChatDTO();
				chat.setChatName(rs.getString(1));
				chat.setChatcontent(rs.getString(2));
				chat.setChatTime(rs.getString(3));
				chatlist.add(chat);
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			dbclose();
		}
		return chatlist;
		
	}
		
	public int submit(String chatName, String chatContent) {
		
		String sql = "insert into chat values (?, ?, sysdate)";
		try {
			
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, chatName);
			pstmt.setString(2, chatContent);
			
			return pstmt.executeUpdate();			
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			dbclose();
		}
		return -1;
	}
		
}
	
		

