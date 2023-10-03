import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

class Solution {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int k = sc.nextInt();
        Solution s = new Solution();

        s.findTheWinner(n, k + 1);
    }

    class Node {
        int x;
        Node next;
        public Node(int x) {
            this.x = x;
        }
    }

    public void findTheWinner(int n, int k) {

        Node temp = build(n);

        StringBuilder sb = new StringBuilder();

        while(temp.next != temp && temp.next.x != -1) {
            Node curr = temp;
            if(k == 1) {
                sb.append(temp.x).append(" ");
                temp = curr.next;
                curr.x = -1;
            }
            else {
                int i = 1;
                while(i < k - 1){
                    curr = curr.next;
                    i++;
                }
                sb.append(curr.next.x).append(" ");
                n--;
                curr.next = curr.next.next;
                temp = curr.next;
            }

        }
        sb.append(temp.x);
        System.out.println(sb);
    }

    private Node build(int n) {
        Node head = new Node(1);
        Node temp = head;
        int i = 2;
        while(i <= n) {
            temp.next = new Node(i);
            temp = temp.next;
            i++;
        }
        temp.next = head;
        return head;
    }
}