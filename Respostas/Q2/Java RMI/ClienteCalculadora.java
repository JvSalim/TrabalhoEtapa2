import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class ClienteCalculadora {
    public static void main(String[] args) throws Exception {
        Registry registro = LocateRegistry.getRegistry("localhost", 1099);
        Calculadora calc = (Calculadora) registro.lookup("ServicoCalculadora");

        System.out.println("Soma: " + calc.somar(5, 3));
        System.out.println("Subtração: " + calc.subtrair(10, 4));
        System.out.println("Multiplicação: " + calc.multiplicar(6, 7));
        System.out.println("Divisão: " + calc.dividir(20, 5));
    }
}
