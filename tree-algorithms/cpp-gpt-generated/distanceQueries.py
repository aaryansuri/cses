import sys
from collections import deque

# Function to find the Lowest Common Ancestor (LCA) of two nodes in a tree
def find_lca(u, v, depths, ancestors):
    if depths[u] > depths[v]:
        u, v = v, u  # Swap u and v if u is deeper than v

    # Bring v up to the same level as u
    diff = depths[v] - depths[u]
    for i in range(len(ancestors[0])):
        if (diff >> i) & 1:
            v = ancestors[v][i]

    # If u and v are the same node, return u
    if u == v:
        return u

    # Move u and v up the tree together until they meet at the LCA
    for i in range(len(ancestors[0]) - 1, -1, -1):
        if ancestors[u][i] != ancestors[v][i]:
            u = ancestors[u][i]
            v = ancestors[v][i]

    return ancestors[u][0]

# Function to calculate the distance between two nodes in a tree
def distance_between_nodes(u, v, depths, ancestors):
    lca = find_lca(u, v, depths, ancestors)
    return depths[u] + depths[v] - 2 * depths[lca]

# Function to build the ancestors and depths arrays using Depth-First Search
def build_tree(n, adj):
    MAXLOG = 18  # Adjust this based on your maximum value of n
    ancestors = [[-1] * (MAXLOG + 1) for _ in range(n + 1)]
    depths = [0] * (n + 1)
    stack = deque([(1, -1, 0)])  # Start DFS at node 1 with parent -1 and depth 0

    while stack:
        node, parent, depth = stack.pop()
        ancestors[node][0] = parent
        depths[node] = depth
        for child in adj[node]:
            if child != parent:
                stack.append((child, node, depth + 1))

    # Build the ancestors array
    for j in range(1, MAXLOG + 1):
        for i in range(1, n + 1):
            parent = ancestors[i][j - 1]
            if parent != -1:
                ancestors[i][j] = ancestors[parent][j - 1]

    return depths, ancestors

def main():
    n, q = map(int, input().split())
    adj = [[] for _ in range(n + 1)]

    for _ in range(n - 1):
        u, v = map(int, input().split())
        adj[u].append(v)
        adj[v].append(u)

    depths, ancestors = build_tree(n, adj)

    for _ in range(q):
        u, v = map(int, input().split())
        result = distance_between_nodes(u, v, depths, ancestors)
        print(result)

if __name__ == "__main__":
    main()
