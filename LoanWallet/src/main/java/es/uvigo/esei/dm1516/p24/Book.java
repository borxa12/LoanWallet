package es.uvigo.esei.dm1516.p24;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by Borxa on 17/11/2015.
 */
public class Book {

    private String tittle;
    private String author;
    private int year;
    private String description;
    private GregorianCalendar date;
    private String library;

    public Book(String tittle, String author, int year, String description, GregorianCalendar date, String library) {
        this.tittle = tittle;
        this.author = author;
        this.year = year;
        this.description = description;
        this.date = date;
        this.library = library;
    }

    public String getTittle() {
        return tittle;
    }

    public String getAuthor() {
        return author;
    }

    public int getYear() {
        return year;
    }

    public String getDescription() {
        return description;
    }

    public GregorianCalendar getDate() {
        return date;
    }

    public String getLibrary() {
        return library;
    }

    public void setTittle(String tittle) {
        this.tittle = tittle;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDate(GregorianCalendar date) {
        this.date = date;
    }

    public void setLibrary(String library) {
        this.library = library;
    }

    @Override
    public String toString() {
        StringBuilder dateAux = new StringBuilder();
        dateAux.append(this.getDate().get(Calendar.DAY_OF_MONTH)).append("/");
        switch(this.getDate().get(Calendar.MONTH)) {
            case 0: dateAux.append("Enero");
                break;
            case 1: dateAux.append("Febrero");
                break;
            case 2: dateAux.append("Marzo");
                break;
            case 3: dateAux.append("Abril");
                break;
            case 4: dateAux.append("Mayo");
                break;
            case 5: dateAux.append("Junio");
                break;
            case 6: dateAux.append("Julio");
                break;
            case 7: dateAux.append("Agosto");
                break;
            case 8: dateAux.append("Septiembre");
                break;
            case 9: dateAux.append("Octubre");
                break;
            case 10: dateAux.append("Noviembre");
                break;
            case 11: dateAux.append("Diciembre");
                break;
        }
        dateAux.append("/").append(this.getDate().get(Calendar.YEAR));

        StringBuilder toret = new StringBuilder();
        toret.append(this.getTittle()).append("(").append(this.getYear()).append(")").append("\n")
                .append(this.getAuthor()).append("\n")
                .append(dateAux.toString()).append(" - ").append(this.getLibrary());

        return toret.toString();
    }
}
