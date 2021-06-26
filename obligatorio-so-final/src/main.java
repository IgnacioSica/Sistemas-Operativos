public class main {
    public static void main(String [] args){
        Moments moments = new Moments(100);
        new Thread(moments).run();
    }
}
