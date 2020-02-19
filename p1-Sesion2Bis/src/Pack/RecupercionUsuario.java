package Pack;

import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import javax.servlet.http.Cookie;

@WebServlet("/RecuperacionUsuario")
public class RecupercionUsuario extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		// Se leen los parámetros
		
		String nombre = request.getParameter("nombre");
		String apellidos = request.getParameter("apellidos");
		String email = request.getParameter("email");
		
		// Se crea el objeto usuario (se supone que existe la clase Usuario)
		
		Usuario usuario = new Usuario(email, nombre, apellidos);
		
		// Y se guarda en una base de datos (igualmente se supone implementada)
		
		UsuarioDB basededatos = new UsuarioDB( );
		basededatos.add (usuario);
		
		//A continuación se guarda en la sesión el mismo objeto que en la base de datos
		HttpSession session = request.getSession( );
		session.setAttribute ("usuario",usuario);
		
		//Y se almacena el email en una cookie para poder identificar en el futuro
		//al usuario mediante su email cuando vuelva a navegar por el sitio web
		Cookie c = new Cookie("emailCookie", email);
		c.setMaxAge(60*60*24*365*2);
		c.setPath("/");
		response.addCookie(c);
		
		Cookie[ ] cookies = request.getCookies( );
		if (cookies!=null){
		for (Cookie cookie: cookies){
		cookie.setMaxAge(0);
		cookie.setPath("/");
		response.addCookie(cookie);
		}
		}
	
		
		
		
		}
}