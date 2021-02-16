package com.neu.edu.LMS.View;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.view.document.AbstractPdfView;
import com.lowagie.text.Document;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.neu.edu.LMS.pojo.User;
import com.neu.edu.LMS.pojo.UserBook;

public class PdfOweBookDetails extends AbstractPdfView{
	
	@Override
	protected void buildPdfDocument(Map model, Document document,
			PdfWriter writer, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		User u = (User) model.get("User");
		List<UserBook> ublist = (List<UserBook>) model.get("userbook");
		
		// Owe Book Table
		PdfPTable tblBook = new PdfPTable(4);
		tblBook.setWidthPercentage(100.0f);
		tblBook.setWidths(new float[] {3.0f, 2.0f, 2.0f, 2.0f});
		tblBook.setSpacingBefore(10);
        
		//Table tblBook = new Table(4);
		
		tblBook.addCell("ISBN");
		tblBook.addCell("CheckOutDate");
		tblBook.addCell("DueDate");
		tblBook.addCell("Fine");

		for (UserBook ub : ublist) {

			tblBook.addCell(ub.getBook().getIsbn());
			tblBook.addCell(ub.getCheckout_date());
			tblBook.addCell(ub.getDue_date());
			tblBook.addCell(ub.getFine().toString());
        }
		  
		document.add(new Paragraph("Chekout books for : "));
		document.add(new Paragraph(" User Name :" + u.getLastName() +","+ u.getName()));
		document.add(new Paragraph(" Lib No    :" + u.getLibRegNum()));
		document.add(new Paragraph(" Email Id  :" + u.getEmail()));
		document.add(tblBook);

	}
}
