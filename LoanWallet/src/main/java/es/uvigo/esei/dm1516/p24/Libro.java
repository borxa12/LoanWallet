package es.uvigo.esei.dm1516.p24;

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
    private String fechaPrestamo;
    private String finPrestamo;
    private String lugarPrestamo;

    public Libro(String ISBN, String titulo, String autores, int ano, String editorial, int renovaciones,
                 String fechaPrestamo, String finPrestamo, String lugarPrestamo) {
        this.ISBN = ISBN;
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

    public String getFechaPrestamo() {
        return fechaPrestamo;
    }

    public void setFechaPrestamo(String fechaPrestamo) {
        this.fechaPrestamo = fechaPrestamo;
    }

    public String getFinPrestamo() {
        return finPrestamo;
    }

    public void setFinPrestamo(String finPrestamo) {
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
        StringBuilder toret = new StringBuilder();
        toret.append(this.getTitulo()).append("(").append(this.getAno()).append(")").append("\n")
                .append(this.getAutores()).append("\n")
                .append(this.getFinPrestamo()).append(" (").append(this.getLugarPrestamo()).append(")");
        return toret.toString();
    }
}
