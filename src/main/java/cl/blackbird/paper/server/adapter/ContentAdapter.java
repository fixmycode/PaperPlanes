package cl.blackbird.paper.server.adapter;


import java.io.OutputStream;

/**
 * Interfaz base para todos los adaptadores
 */
public interface ContentAdapter {
    /**
     * Obtiene el tipo de contenido del adaptador
     * @return un String con el tipo de contenido en formato MIME
     */
    public String getContentType();

    /**
     * Escribe el contenido al flujo de salida
     * @param out el flujo donde escribir la salida
     */
    public void writeContent(OutputStream out);
}
