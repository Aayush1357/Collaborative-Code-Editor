class Solution {

    int totalsum;
    public int findTargetSumWays(int[] nums, int target) {
        int n = nums.length;

        totalsum = Arrays.stream(nums).sum();

        int[][] dp = new int[n][2 * totalsum + 1];
        for(int[] row : dp){
            Arrays.fill(row , Integer.MIN_VALUE);
        }

        return f(0 , target , 0 , nums , dp);
    }

    public int f(int i , int t , int v , int[] nums , int[][] dp){

        if(i == nums.length){
            if(t == v) return 1;
            else return 0;
        }

        if(dp[i][v + totalsum] != Integer.MIN_VALUE) return dp[i][v + totalsum];

        int add = f(i+1 , t, v + nums[i] , nums ,dp);
        int subtract = f(i+1 , t , v - nums[i] , nums , dp);

        return dp[i][v + totalsum] = add + subtract;
    }
}