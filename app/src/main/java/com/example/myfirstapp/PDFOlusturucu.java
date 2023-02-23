package com.example.myfirstapp;

import android.content.Context;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;


import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.List;
import com.itextpdf.text.ListItem;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;

public class PDFOlusturucu {
    public void pdfOlustur(Context context, String a, List alinanList,List verilenList,List gunlukRapor) throws DocumentException {
        String title=a+"  Tarihli Rapor";

        Document document=new Document();
        document.setPageSize(PageSize.A4);

        try{
            Log.e("5","e");
            String pdfPath=Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString();

            PdfWriter.getInstance(document,new FileOutputStream(new File(pdfPath,a+".pdf")));

        }catch (FileNotFoundException | DocumentException e){
            e.printStackTrace();
            Toast.makeText(context, "Pdf Dosyası Oluşturulamadı", Toast.LENGTH_SHORT).show();
        }
        document.open();
        Font fontBaslik = new Font(Font.FontFamily.HELVETICA,30.0f, Font.NORMAL, BaseColor.BLACK);
        Chunk baslik_yazisi=new Chunk(title,fontBaslik);
        Paragraph baslik=new Paragraph(baslik_yazisi);
        baslik.setAlignment(Element.ALIGN_CENTER);
        document.add(baslik);

        PdfPTable table=new PdfPTable(2);
        PdfPCell alinanHucre=new PdfPCell();
        PdfPCell verilenHucre=new PdfPCell();
        alinanHucre.addElement(alinanList);
        verilenHucre.addElement(verilenList);
        table.addCell(alinanHucre);
        table.addCell(verilenHucre);
        document.add(table);
        document.add(gunlukRapor);
        document.close();
        Toast.makeText(context,"Pdf dosyanız İndirilenler klasörü içerisine oluşturuldu",Toast.LENGTH_LONG).show();

    }
}
