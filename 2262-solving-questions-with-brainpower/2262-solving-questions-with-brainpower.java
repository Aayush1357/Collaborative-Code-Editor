class Solution {
    public long mostPoints(int[][] questions) {

        int n = questions.length;
        long[] dp = new long[n];
        Arrays.fill(dp , -1);

        return f(0 , questions , dp);
    }


    public static long f(int i , int[][] questions , long[] dp){

        if(i >= questions.length){
            return 0;
        }
        if(dp[i] != -1){
            return dp[i];
        }
        int skip = questions[i][1];

        long take = questions[i][0] + f(i+skip+1  , questions , dp);
        long nottake = f(i+1 , questions ,dp);

        return dp[i] = Math.max(take , nottake);
    }
}