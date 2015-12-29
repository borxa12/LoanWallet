package es.uvigo.esei.dm1516.p24;

/**
 * Created by Borxa on 17/11/2015.
 */
public class Pelicula {

    private String titulo;
    private String director;
    private int ano;
    private int duracion;
    private String genero;
    private int renovaciones;
    private String fechaPrestamo;
    private String finPrestamo;
    private String lugarPrestamo;

    public Pelicula(String titulo, String director, int ano, int duracion, String genero, int renovaciones,
                    String fechaPrestamo, String finPrestamo, String lugarPrestamo) {
        this.titulo = titulo;
        this.director = director;
        this.ano = ano;
        this.duracion = duracion;
        this.genero = genero;
        this.renovaciones = renovaciones;
        this.fechaPrestamo = fechaPrestamo;
        this.finPrestamo = finPrestamo;
        this.lugarPrestamo = lugarPrestamo;
    }

    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public int getDuracion() {
        return duracion;
    }

    public void setDuracion(int duracion) {
        this.duracion = duracion;
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

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getLugarPrestamo() {
        return lugarPrestamo;
    }

    public void setLugarPrestamo(String lugarPrestamo) {
        this.lugarPrestamo = lugarPrestamo;
    }

    public int getRenovaciones() {
        return renovaciones;
    }

    public void setRenovaciones(int renovaciones) {
        this.renovaciones = renovaciones;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    @Override
    public String toString() {
        StringBuilder toret = new StringBuilder();
        toret.append(this.getTitulo()).append("(").append(this.getAno()).append(") - ").append(this.getDuracion()).append(" min\n")
                .append(this.getDirector()).append("\n")
                .append(this.getFinPrestamo()).append(" (").append(this.getLugarPrestamo()).append(")");
        return toret.toString();
    }
}
