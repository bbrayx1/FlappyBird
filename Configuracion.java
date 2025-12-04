public class Configuracion {
    // 1. CONFIGURACIÓN DE PANTALLA ---
    public static int SCREEN_WIDTH = 360;
    public static int SCREEN_HEIGHT = 640;

    // 2. PERSONALIZACIÓN VISUAL (Skins) ---
    // Opciones: "amarillo", "morado", "celeste"
    public static String BIRD_SKIN = "yellow"; 
    
    // Opciones: "verde", "rojo"
    public static String PIPE_SKIN = "green"; 
    
    // Opciones: "dia", "noche, extra: navideño"
    public static String BACKGROUND_SKIN = "day"; 

    // 3. DIFICULTAD DINÁMICA ---
    // ¿Cada cuántos puntos aumenta la velocidad? (0 = nunca)
    public static int POINTS_TO_ACCELERATE = 5; 

    //4. TAMAÑOS ---
    public static int AVE_ANCHO = 34;
    public static int AVE_ALTO = 24;
    
    public static int AVE_POS_X = SCREEN_WIDTH / 8;
    public static int AVE_POS_Y = SCREEN_HEIGHT / 2;
    public static int TUBERIA_ANCHO = 64;
    public static int TUBERIA_ALTO = 512;

    //5. FÍSICA INICIAL
    public static int FRECUENCIA_TUBERIAS = 1500;    
    public static int VELOCIDAD_DESPLAZAMIENTO = -4; 
    public static int VELOCIDAD_SALTO = -9;          
    public static int GRAVEDAD = 1;                  
}