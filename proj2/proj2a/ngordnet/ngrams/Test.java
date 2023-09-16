package ngordnet.ngrams;

public class Test {
    static int arr[] = { 1, 2, 3, 4, 5 };

    // Return sum of elements in A[0..N-1]
    // using recursion.
    static int findSum(int A[], int N)
    {
        if (N <= 0)
            return 0;
        int num = A[N - 1];
        return (findSum(A, N - 1) + num);
    }

    // Driver method
    public static void main(String[] args)
    {
        int sum = findSum(arr, arr.length);
    }
}
