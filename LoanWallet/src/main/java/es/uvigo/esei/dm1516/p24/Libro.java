package es.uvigo.esei.dm1516.p24;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by Borxa on 17/11/2015.
 */
public class Libro {

    private String ISBN;
    private String titulo;
    private String autores;
    private int ano;
    private String editorial;
    private int renovaciones;
    private GregorianCalendar fechaPrestamo;
    private GregorianCalendar finPrestamo;
    private String lugarPrestamo;

    public Libro(String ISBN, String titulo, String autores, int ano, String editorial, int renovaciones,
                GregorianCalendar fechaPrestamo, GregorianCalendar finPrestamo, String lugarPrestamo) {
        this.titulo = titulo;
        this.autores = autores;
        this.ano = ano;
        this.editorial = editorial;
        this.renovaciones = renovaciones;
        this.fechaPrestamo = fechaPrestamo;
        this.finPrestamo = finPrestamo;
        this.lugarPrestamo = lugarPrestamo;
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutores() {
        return autores;
    }

    public void setAutores(String autores) {
        this.autores = autores;
    }

    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    public String getEditorial() {
        return editorial;
    }

    public void setEditorial(String editorial) {
        this.editorial = editorial;
    }

    public int getRenovaciones() {
        return renovaciones;
    }

    public void setRenovaciones(int renovaciones) {
        this.renovaciones = renovaciones;
    }

    public GregorianCalendar getFechaPrestamo() {
        return fechaPrestamo;
    }

    public void setFechaPrestamo(GregorianCalendar fechaPrestamo) {
        this.fechaPrestamo = fechaPrestamo;
    }

    public GregorianCalendar getFinPrestamo() {
        return finPrestamo;
    }

    public void setFinPrestamo(GregorianCalendar finPrestamo) {
        this.finPrestamo = finPrestamo;
    }

    public String getLugarPrestamo() {
        return lugarPrestamo;
    }

    public void setLugarPrestamo(String lugarPrestamo) {
        this.lugarPrestamo = lugarPrestamo;
    }

    @Override
    public String toString() {
        StringBuilder dateAux = new StringBuilder();
        dateAux.append(this.getFinPrestamo().get(Calendar.DAY_OF_MONTH)).append("/");
        switch(this.getFinPrestamo().get(Calendar.MONTH)) {
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
        dateAux.append("/").append(this.getFinPrestamo().get(Calendar.YEAR));

        StringBuilder toret = new StringBuilder();
        toret.append(this.getTitulo()).append("(").append(this.getAno()).append(")").append("\n")
                .append(this.getAutores()).append("\n")
                .append(dateAux.toString()).append(" - ").append(this.getLugarPrestamo());

        return toret.toString();
    }
}
