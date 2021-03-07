package org.jesuitasrioja.colegio.persistencia;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.jesuitasrioja.colegio.modelo.Alumno;
import org.jesuitasrioja.colegio.modelo.Asignatura;
import org.jesuitasrioja.colegio.modelo.Libro;

public class PersistenciaJDBC {
	private String url;
	private String user;
	private String password;
	public PersistenciaJDBC() {
		Properties p = new Properties();
		try {
			p.load(new FileInputStream("propiedades.properties"));
			
			this.url=p.getProperty("url");
			this.user=p.getProperty("usr");
			this.password=p.getProperty("pass");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void cambiarNombreAlumno(Long idAlumno, String nombreNuevo) {
		Connection con = null;
		
		Alumno lastAlumno = this.getAlumno(idAlumno);
		
		Alumno newAlumno = null;
		
		try {
			con= DriverManager.getConnection(url, user, password);
			
			PreparedStatement ps = con.prepareStatement("UPDATE alumno Set name = ? WHERE ID =?");
			ps.setString(1,nombreNuevo);
			ps.setFloat(2,idAlumno);
			
			int rows = ps.executeUpdate();
			System.out.println("se ha ejecutado la consulta con un total de "+ rows+"fila afectada");
			
			ps= con.prepareStatement("Select * from alumno where ID=?");
			ps.setFloat(1, idAlumno);
			
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				newAlumno = new Alumno(rs.getFloat(1), rs.getString(2), rs.getString(3));
			}
			if(!lastAlumno.equals(newAlumno)) {
				
				System.out.println("se ha modificado");				
			}else {
				System.out.println("no se ha modificado");
				
			}
			con.close();
			ps.close();
			rs.close();	
	}catch (Exception e) {
		e.printStackTrace();
	}finally {
		try {
		if(con!=null) {
			con.close();
		}
	}catch(Exception e2) {
		e2.printStackTrace();
	}


		}
	}
	public void nuevaAsignatura(Asignatura asignatura) {
		Connection con =null;
		try {
		con=DriverManager.getConnection(url,user,password);
		PreparedStatement ps1= con.prepareStatement("select* from asignatura where id=? ");
		ps1.setString(1,asignatura.getid());
		ResultSet rsAsignatura = ps1.executeQuery();
		if(!rsAsignatura.next()) {
			Asignatura nuevaAsignatura = asignatura.getid();
			PreparedStatement ps2 = con.prepareStatement("insert into asignatura values(?,?)");
			ps2.setString(1,nuevaAsignatura.getnombre());
			ps2.setInt(2,nuevaAsignatura.getid());
			ps2.executeUpdate();
			ps2.close();
		}
		rsAsignatura.close();
		ps1.close();
		PreparedStatement ps = con.prepareStatement("insert into libro values (?,?)");
		ps.setString(1,asignatura.getisbn());
		ps.setString(2, asignatura.gettitulo());
		int i = ps.executeUpdate();
		ps.close();
		con.commit();
		
		} catch (SQLException e) {
		if(con!=null)
			try {
				con.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			e.printStackTrace();
		}finally {
			try {
			if(con!=null) con.close();
			}catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		
	}
	
	public List<Libro> librosDeAlumno(Long idAlumno){
		Connection con = null;
		ArrayList<Libro> lstLibro = new ArrayList<>();
		try {
			con = DriverManager.getConnection(url, user, password);
			PreparedStatement ps = con.prepareStatement("Select * from libro");

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				Libro libro = new Libro(rs.getString(1), rs.getString(2));
				lstLibro.add(libro);
			}
			con.close();
			ps.close();
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lstLibro;
	}
}
