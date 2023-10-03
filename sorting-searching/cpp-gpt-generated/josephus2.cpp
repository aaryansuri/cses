#include <iostream>

class Solution {
public:
    class Node {
    public:
        int x;
        Node* next;
        Node(int val) : x(val), next(nullptr) {}
    };

    void findTheWinner(int n, int k) {
        Node* temp = build(n);
        std::string result;

        while (temp->next != temp && temp->next->x != -1) {
            Node* curr = temp;
            if (k == 1) {
                result += std::to_string(temp->x) + " ";
                temp = curr->next;
                curr->x = -1;
            } else {
                int i = 1;
                while (i < k - 1) {
                    curr = curr->next;
                    i++;
                }
                result += std::to_string(curr->next->x) + " ";
                Node* toDelete = curr->next;
                curr->next = curr->next->next;
                delete toDelete;
                temp = curr->next;
            }
        }
        result += std::to_string(temp->x);
        std::cout << result << std::endl;
    }

private:
    Node* build(int n) {
        Node* head = new Node(1);
        Node* temp = head;
        int i = 2;
        while (i <= n) {
            temp->next = new Node(i);
            temp = temp->next;
            i++;
        }
        temp->next = head;
        return head;
    }
};

int main() {
    int n, k;
    std::cin >> n >> k;
    Solution s;
    s.findTheWinner(n, k + 1);
    return 0;
}
