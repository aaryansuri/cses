#include <iostream>
#include <vector>
using namespace std;

void buildY(vector<vector<int>>& tree, const vector<vector<int>>& forest, int vx, int startX, int endX, int vy, int startY, int endY) {
    if (startY == endY) {
        if (startX == endX) {
            tree[vx][vy] = forest[startX][startY];
        } else {
            tree[vx][vy] = tree[2 * vx + 1][vy] + tree[2 * vx + 2][vy];
        }
    } else {
        int mid = (startY + endY) / 2;
        buildY(tree, forest, vx, startX, endX, 2 * vy + 1, startY, mid);
        buildY(tree, forest, vx, startX, endX, 2 * vy + 2, mid + 1, endY);
        tree[vx][vy] = tree[vx][2 * vy + 1] + tree[vx][2 * vy + 2];
    }
}

void buildX(vector<vector<int>>& tree, const vector<vector<int>>& forest, int vx, int startX, int endX, int n) {
    if (startX == endX) {
        buildY(tree, forest, vx, startX, endX, 0, 1, n);
    } else {
        int mid = (startX + endX) / 2;
        buildX(tree, forest, 2 * vx + 1, startX, mid, n);
        buildX(tree, forest, 2 * vx + 2, mid + 1, endX, n);
        buildY(tree, forest, vx, startX, endX, 0, 1, n);
    }
}

int queryY(const vector<vector<int>>& tree, int vx, int vy, int y1, int y2, int startY, int endY) {
    if (endY < y1 || startY > y2) return 0;
    if (startY >= y1 && endY <= y2) {
        return tree[vx][vy];
    }
    int mid = (startY + endY) / 2;
    return queryY(tree, vx, 2 * vy + 1, y1, y2, startY, mid)
         + queryY(tree, vx, 2 * vy + 2, y1, y2, mid + 1, endY);
}

int queryX(const vector<vector<int>>& tree, int vx, int x1, int y1, int startX, int endX, int x2, int y2, int n) {
    if (endX < x1 || startX > x2) return 0;
    if (startX >= x1 && endX <= x2) {
        return queryY(tree, vx, 0, y1, y2, 1, n);
    }
    int mid = (startX + endX) / 2;
    return queryX(tree, 2 * vx + 1, x1, y1, startX, mid, x2, y2, n)
         + queryX(tree, 2 * vx + 2, x1, y1, mid + 1, endX, x2, y2, n);
}

void updateY(vector<vector<int>>& tree, int vx, int vy, int startX, int endX, int startY, int endY, int posY, int val) {
    if (startY == endY) {
        if (startX == endX) {
            tree[vx][vy] += val;
        } else {
            tree[vx][vy] = tree[2 * vx + 1][vy] + tree[2 * vx + 2][vy];
        }
    } else {
        int mid = (startY + endY) / 2;
        if (posY <= mid) {
            updateY(tree, vx, 2 * vy + 1, startX, endX, startY, mid, posY, val);
        } else {
            updateY(tree, vx, 2 * vy + 2, startX, endX, mid + 1, endY, posY, val);
        }
        tree[vx][vy] = tree[vx][2 * vy + 1] + tree[vx][2 * vy + 2];
    }
}

void updateX(vector<vector<int>>& tree, int vx, int startX, int endX, int n, int posX, int posY, int val) {
    if (startX == endX) {
        updateY(tree, vx, 0, startX, endX, 1, n, posY, val);
    } else {
        int mid = (startX + endX) / 2;
        if (posX <= mid) {
            updateX(tree, 2 * vx + 1, startX, mid, n, posX, posY, val);
        } else {
            updateX(tree, 2 * vx + 2, mid + 1, endX, n, posX, posY, val);
        }
        updateY(tree, vx, 0, startX, endX, 1, n, posY, val);
    }
}



int main() {
    ios_base::sync_with_stdio(false);
        cin.tie(NULL);

        int n, q;
        cin >> n >> q;

        vector<vector<int>> forest(n + 1, vector<int>(n + 1, 0));
        vector<vector<int>> tree(4 * n, vector<int>(4 * n, 0));

        for (int i = 1; i <= n; i++) {
            string line;
            cin >> line;
            line = "x" + line;
            for (int j = 1; j <= n; j++) {
                forest[i][j] = (line[j] == '*') ? 1 : 0;
            }
        }

    buildX(tree, forest, 0, 1, n, n);

    string result = "";

    for (int i = 0; i < q; i++) {
            int type;
            cin >> type;
            if (type == 2) {
                int x1, y1, x2, y2;
                cin >> x1 >> y1 >> x2 >> y2;
                cout << queryX(tree, 0, x1, y1, 1, n, x2, y2, n) << "\n";
            } else {
                int posX, posY;
                cin >> posX >> posY;
                int val = (forest[posX][posY] == 1) ? -1 : 1;
                forest[posX][posY] = val;
                updateX(tree, 0, 1, n, n, posX, posY, val);
            }
        }

        return 0;
}
