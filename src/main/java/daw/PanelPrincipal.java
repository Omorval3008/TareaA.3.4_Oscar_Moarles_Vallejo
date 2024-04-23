/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package daw;

/**
 *
 * @author Óscar
 */
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;

/**
 * @author Juan Carlos Fernández Vico
 */
public class PanelPrincipal extends JPanel implements ActionListener {

    // Atributos de la clase (privados)
    private PanelBotones botonera;
    private JTextArea areaTexto;
    private int tipoOperacion;
    private String operacion = "";

    // Constructor
    public PanelPrincipal() {
        initComponents();
        tipoOperacion = -1; // No hay operaciones en la calculadora
    }

    // Se inicializan los componentes gráficos y se colocan en el panel
    private void initComponents() {
        // Creamos el panel de botones
        botonera = new PanelBotones();
        // Creamos el área de texto
        areaTexto = new JTextArea(10, 50);
        areaTexto.setEditable(false);
        areaTexto.setBackground(Color.white);

        //Establecemos layout del panel principal
        this.setLayout(new BorderLayout());
        // Colocamos la botonera y el área texto
        this.add(areaTexto, BorderLayout.NORTH);
        this.add(botonera, BorderLayout.SOUTH);

        for (JButton boton : this.botonera.getgrupoBotones()) {
            boton.addActionListener(e -> {
                actionPerformed(e);
            });
        }

    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        // Se obtiene el objeto que desencadena el evento
        Object o = ae.getSource();
        // Si es un botón
        if (o instanceof JButton) {
            if (((JButton) o).getText().equalsIgnoreCase("=")) {
                operaciones(operacion);
                try {
                    if(resultadoOperacion()!=null){
                        areaTexto.setText(""+resultadoOperacion());
                        this.tipoOperacion=-1;this.operacion="";
                    }else{
                        System.out.println("Nulooo");
                    }
                } catch (Exception ex) {
                    areaTexto.setText("SYNTAX ERROR!");
                    JOptionPane.showMessageDialog(null,"No puede haber más de un operando o dos operandos distintos");
                    operacion="";tipoOperacion=-1;
                    
                }
            } else if (((JButton) o).getText().equalsIgnoreCase("c")) {
                this.operacion = "";
                areaTexto.setText(this.operacion);
            } else {
                this.operacion = this.operacion + ((JButton) o).getText();
                areaTexto.setText(this.operacion);
            }
        }

    }

    public void operaciones(String operacion) {
        char[] arrayOperaciones = operacion.toCharArray();
        for (int i = 0; i < arrayOperaciones.length; i++) {
            if (arrayOperaciones[i] == '+') {
                this.tipoOperacion = 1;
                break;
            } else if (arrayOperaciones[i] == '-') {
                this.tipoOperacion = 2;
                break;
            } else if (arrayOperaciones[i] == '*') {
                this.tipoOperacion = 3;
                break;
            } else if(arrayOperaciones[i] == '/') {
                this.tipoOperacion = 4;
                break;
            }
        }
    }

    public Double resultadoOperacion() throws Exception {
        Double resultado = null;
        String[] numeros;
        switch (tipoOperacion) {
            case 1 -> {
                if (operacion.contains("-") || operacion.contains("*") || operacion.contains("/")) {
                    throw new Exception("No puede haber más de un operando o operandos distintos");
                } else {
                    numeros = this.operacion.split("\\+");
                    resultado=0.0;
                    for(int i=0;i<numeros.length;i++){
                             resultado+= Double.parseDouble(numeros[i]);
                    }
                }
                
            }
            case 2 -> {
                if (operacion.contains("+") || operacion.contains("*") || operacion.contains("/")) {
                     throw new Exception("No puede haber más de un operando o operandos distintos");
                } else {
                   numeros = this.operacion.split("\\-");
                    System.out.println(Arrays.toString(numeros));
                    resultado=0.0;
                    for(int i=0;i<numeros.length;i++){
                             resultado-= Double.parseDouble(numeros[i]);
                    }
                }

            }
            case 3 -> {
                if (operacion.contains("-") || operacion.contains("+") || operacion.contains("/")) {
                     throw new Exception("No puede haber más de un operando o operandos distintos");
                } else {
                    numeros = this.operacion.split("\\*");
                    System.out.println(Arrays.toString(numeros));
                    resultado=1.0;
                    for(int i=0;i<numeros.length;i++){
                             resultado*= Double.parseDouble(numeros[i]);
                    }
                }

            }
            case 4 -> {
                if (operacion.contains("-") || operacion.contains("*") || operacion.contains("+")) {
                     throw new Exception("No puede haber más de un operando o operandos distintos");
                } else {
                   numeros = this.operacion.split("\\/");
                    System.out.println(Arrays.toString(numeros));
                    resultado=1.0;
                    for(int i=0;i<numeros.length;i++){
                             resultado/= Double.parseDouble(numeros[i]);
                    }
                }

            }
        }
        return resultado;
    }
}
