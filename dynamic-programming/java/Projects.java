import java.util.Arrays;
import java.util.Comparator;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.TreeSet;

public class Projects {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();

        Project[] projects = new Project[n];

        for(int i = 0; i < n; i++) {
            projects[i] = new Project();
            projects[i].start = sc.nextInt();
            projects[i].end = sc.nextInt();
            projects[i].reward = sc.nextInt();
        }

        Arrays.sort(projects, Comparator.comparing(Project::getEnd).thenComparing(Project::getStart));
        System.out.println(maxMoneyIDP(projects, projects.length));

    }

    private static long maxMoney(Project[] projects, int curr, long prevStart) {

        if(curr == -1) return 0;
        long max = Integer.MIN_VALUE;

        if(prevStart > projects[curr].end) {
            max = Math.max(max, projects[curr].reward + maxMoney(projects, curr - 1, projects[curr].start));
        }
        max = Math.max(max, maxMoney(projects, curr - 1, prevStart));
        return max;
    }


    private static long maxMoneyIDP(Project[] projects, int n) {

        long max = 0;

        TreeSet<Project> heap = new TreeSet<>(Comparator.comparing(Project::getEnd).thenComparing(Project::getRewardsAccumulated));

        for (Project project : projects) {

            Project lessThanCurr = heap.lower(new Project(0, project.start, 0));

            if (lessThanCurr != null) {
                project.rewardsAccumulated = project.reward + lessThanCurr.rewardsAccumulated;
            } else {
                project.rewardsAccumulated = project.reward;
            }

            max = Math.max(project.rewardsAccumulated, max);
            project.rewardsAccumulated = max;
            heap.add(project);
        }

        return max;
    }

    static class Project {
        long start;  long end; long reward;
        long rewardsAccumulated;

        public Project() {
        }

        public Project(long start, long end, long reward) {
            this.start = start;
            this.end = end;
            this.reward = reward;
        }

        public void setRewardsAccumulated(long rewardsAccumulated) {
            this.rewardsAccumulated = rewardsAccumulated;
        }

        public long getStart() {
            return start;
        }

        public long getEnd() {
            return end;
        }

        public long getReward() {
            return reward;
        }

        public long getRewardsAccumulated() {
            return rewardsAccumulated;
        }

    }
}
