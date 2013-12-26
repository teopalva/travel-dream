package servlet;

import java.io.IOException;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import coreEJB.PackageEJBLocal;
import dto.PackageDTO;
import exceptions.NotValidPackageException;

/**
 * Servlet implementation class ImageServlet
 */
@WebServlet("/ImageServlet")
public class ImageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	@EJB
    private PackageEJBLocal packageEJB;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ImageServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String stringId = request.getParameter("id");
		
		int id = -1;
		if(stringId != null)
			id =Integer.parseInt(stringId);
		PackageDTO _package = new PackageDTO();
		_package.setId(id);
        byte[] image;
        
        ServletOutputStream outputStream = response.getOutputStream();
        
		try {
			image = packageEJB.getPackageImage(_package);
			response.setContentType("image/jpeg");
	        
	        outputStream.write(image);
		} catch (NotValidPackageException e) {
			outputStream.println("Id pacchetto non valido");
		}

        
        outputStream.close();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
