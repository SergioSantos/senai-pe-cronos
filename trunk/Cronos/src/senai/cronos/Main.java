package senai.cronos;

import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.lang.Object;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import senai.cronos.gui.CronosFrame;
import senai.cronos.util.Calendario;
import senai.cronos.util.Feriado;
import org.apache.derby.drda.NetworkServerControl;
import org.apache.derby.impl.drda.NetworkServerControlImpl;

/**
 *
 * @author sergio lisan e carlos melo
 */
public class Main {

    public static void main(String[] args) {
        Main m = new Main();
try {
    String location=System.getProperty("user.dir")+System.getProperty("file.separator"); 
    System.setProperty("derby.system.home", location);
    NetworkServerControlImpl networkServer = new NetworkServerControlImpl();
    networkServer.start(new PrintWriter(System.out));
    System.out.println("Conectado ao banco de dados.");
        } catch (Exception ex) {
        System.out.println("Não conseguiu conectar no banco de dados.");
    }
        try {
            m.init();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "FAIL!\n" + e);
            e.printStackTrace();
        }
    }

    /**
     * Metodo que carrega o sistema
     */
    public void init() {
        try {
            loadPreferences();
        } catch (ClassNotFoundException | SQLException | ParseException ex) {
            JOptionPane.showMessageDialog(null, "FAIL! Problemas ao iniciar o sistema:\n\n" + ex.getMessage());
        }

        loadUI();
    }

    /**
     * Carrega as configuracoes do sistema
     */
    private void loadPreferences() throws ClassNotFoundException, SQLException, ParseException {
        Date inicio = Fachada.getInicioCalendario();
        Date fim = Fachada.getFimCalendario();

        List<Feriado> feriados = Fachada.<Feriado>get(Feriado.class);
        
        List<Date> diasDeFeriado = new ArrayList<>();
        for(Feriado f : feriados) {
            diasDeFeriado.add(f.getDia());
        }
        
        calendario = new Calendario(inicio, fim, diasDeFeriado);
    }

    /**
     * Carrega e inicia a Interface Gráfica
     */
    private void loadUI() {
        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                CronosFrame frame = new CronosFrame();
                frame.setVisible(true);
            }
        });
    }
    
    /**
     * objeto que armazena o calendario de dias uteis usados pela escola
     */
    public static Calendario calendario;
}
