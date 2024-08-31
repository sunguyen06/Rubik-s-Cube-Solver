package FRONTEND;

public class MergeSort {

    public static void mergeArrays(SubmissionEntry[] arr, SubmissionEntry[] leftArray, SubmissionEntry[] rightArray, String sortBy) {
        
        int leftArraySize = leftArray.length;
        int rightArraySize = rightArray.length;

        int leftArrayIndex = 0;
        int rightArrayIndex = 0;
        int mainArrayIndex = 0;

        while (leftArrayIndex < leftArraySize && rightArrayIndex < rightArraySize) {

            if (sortBy.equals("TIME")) {
                if (leftArray[leftArrayIndex].solveTimeToInt() <= rightArray[rightArrayIndex].solveTimeToInt()) {
                    arr[mainArrayIndex] = leftArray[leftArrayIndex];
                    mainArrayIndex++;
                    leftArrayIndex++;
                }
                else {
                    arr[mainArrayIndex] = rightArray[rightArrayIndex];
                    mainArrayIndex++;
                    rightArrayIndex++;
                }
            }
            else if (sortBy.equals("DATE")) {
                if (leftArray[leftArrayIndex].dateToLocalDate().isAfter(rightArray[rightArrayIndex].dateToLocalDate())) {
                    arr[mainArrayIndex] = leftArray[leftArrayIndex];
                    mainArrayIndex++;
                    leftArrayIndex++;
                }
                else {
                    arr[mainArrayIndex] = rightArray[rightArrayIndex];
                    mainArrayIndex++;
                    rightArrayIndex++;
                }
            }
        }
        // after emptying one list, empty the rest as all values in it would be at the end of the new merged list

        while (leftArrayIndex < leftArraySize) {
                arr[mainArrayIndex] = leftArray[leftArrayIndex];
                mainArrayIndex++;
                leftArrayIndex++;
        }
        while (rightArrayIndex < rightArraySize) {
                arr[mainArrayIndex] = rightArray[rightArrayIndex];
                mainArrayIndex++;
                rightArrayIndex++;
        }
    }

    public static void mergeSort(SubmissionEntry[] arr, String sortBy) {
        int arrayLength = arr.length;
        if (arrayLength <= 1) {
            return;
        }
        int partitionLength = arrayLength/2;
        SubmissionEntry[] leftArray = new SubmissionEntry[partitionLength];
        SubmissionEntry[] rightArray = new SubmissionEntry[arrayLength-partitionLength];

        int i = 0;

        while (i < partitionLength) {
            leftArray[i] = arr[i];
            i++;
        }
        while (i < arrayLength) {
            rightArray[i-partitionLength] = arr[i];
            i++;
        }

        mergeSort(leftArray, sortBy);
        mergeSort(rightArray, sortBy);

        mergeArrays(arr, leftArray, rightArray, sortBy);

    }
}
