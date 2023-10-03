class SubTreeQueries:

    def __init__(self):
        self.id = 0

    def dfsForSubTree(self, x, parent, adj, subTreeSizes, lookup, reverseLookUp):
        self.id += 1
        lookup[self.id] = x
        reverseLookUp[x] = self.id
        for neigh in adj[x]:
            if neigh == parent:
                continue
            self.dfsForSubTree(neigh, x, adj, subTreeSizes, lookup, reverseLookUp)
            subTreeSizes[x] += 1 + subTreeSizes[neigh]

    def sum(self, BIT, k):
        sum = 0
        while k > 0:
            sum += BIT[k]
            k -= k & -k
        return sum

    def update(self, BIT, k, v):
        while k <= len(BIT):
            BIT[k] += v
            k += k & -k

    def main(self):
        n, q = map(int, input().split())

        values = [int(input()) for _ in range(1, n + 1)]
        subTreeSizes = [0] * (n + 1)
        adj = [[] for _ in range(n + 1)]
        BIT = [0] * (n + 1)
        lookup = [0] * (n + 1)
        reverseLookUp = [0] * (n + 1)

        for i in range(n - 1):
            a, b = map(int, input().split())
            adj[a].append(b)
            adj[b].append(a)

        self.dfsForSubTree(1, -1, adj, subTreeSizes, lookup, reverseLookUp)

        for i in range(1, n + 1):
            self.update(BIT, reverseLookUp[i], values[i])

        result = ""

        for i in range(q):
            type = int(input())
            if type == 1:
                s, x = map(int, input().split())
                self.update(BIT, reverseLookUp[s], x - values[s])
                values[s] = x
            else:
                s = int(input())
                subTreeSize = subTreeSizes[s]
                result += str(self.sum(BIT, reverseLookUp[s] + subTreeSize) - self.sum(BIT, reverseLookUp[s] - 1)) + "\n"

        print(result)
