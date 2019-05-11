public class TaskImpl implements Task {
    @Override
    public Object compute(Params p) {
        int c0 = p.c0;
        int c1 = p.c1;
        int c2 = 0;

        for (int i = 2; i <= p.cn; i++) {
            c2 = c0 + c1;
            c0 = c1;
            c1 = c2;
        }

        return c2;
    }
}
