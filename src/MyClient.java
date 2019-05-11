import java.io.FileInputStream;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class MyClient {
    static void addClassToWorker(Worker worker1, String className) throws RemoteException {
        if (!worker1.hasClassCode(className)) {
            FileInputStream in = null;
            try {
                in = new FileInputStream(className + ".class");
                byte[] t = in.readAllBytes();
                in.close();
                worker1.storeClassCode(className, t);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        Task task = new TaskImpl();
        Object result1;
        Object result2;
        Params params1 = new Params();
        params1.c0 = 0;
        params1.c1 = 1;
        params1.cn = 10;

        Params params2 = new Params();

        if (args.length <= 1) {
            System.out.println("You have to enter RMI object address in the form: //host_address/service_name //host_name/service_name");
            return;
        }

        String address = args[0];
        String address_2 = args[1];

//        if (System.getSecurityManager() == null){
//            System.setSecurityManager(new SecurityManager());
//        }

        Worker worker1 = null;
        Worker worker2 = null;

        try {
            worker1 = (Worker) java.rmi.Naming.lookup(address);
            worker2 = (Worker) java.rmi.Naming.lookup(address_2);
        } catch (NotBoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("Nie mozna pobrac referencji do " + address);
            e.printStackTrace();
            return;
        }

        System.out.println("Referencja do " + address + " jest pobrana");
        System.out.println("Referencja do " + address_2 + " jest pobrana");

        try {
            String className = task.getClass().getName();
            addClassToWorker(worker1, className);
            addClassToWorker(worker2, className);

            result1 = worker1.compute(task, params1);
            params2.c0 = (int) result1;
            params2.c1 = 45;
            params2.cn = 100;
            result2 = worker2.compute(task, params2);

        } catch (Exception e) {
            System.out.print("Blad zdalnego wywolania.");
            e.printStackTrace();
            return;
        }

        System.out.println("Wynik1 = " + result1);
        System.out.println("Wynik2 = " + result2);
    }
}
