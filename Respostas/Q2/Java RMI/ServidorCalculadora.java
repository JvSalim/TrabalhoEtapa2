import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class ServidorCalculadora {
    public static void main(String[] args) throws Exception {
        Calculadora calc = new CalculadoraImpl();
        Registry registro = LocateRegistry.createRegistry(1099);
        registro.rebind("ServicoCalculadora", calc);
        System.out.println("Servidor RMI em execução...");
    }
}
