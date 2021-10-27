package com.anish.calabashbros;

public class QuickSorter2D <T extends Comparable<T>> implements Sorter2D<T>{

    private T[][] a;
    private int column;
    private int row;

    @Override
    public void load(T[][] elements) {
        this.a=elements;
        this.column = a[1].length;
        this.row = a.length;
    }

    @Override
    public void sort() {
        int[] left={0,0};
        int[] right={row-1,column-1};
        quickSorter2DExecute(left, right);
    }

    private void swap(int[] i, int[] j) {
        T temp;
        temp = a[i[0]][i[1]];
        a[i[0]][i[1]] = a[j[0]][j[1]];
        a[j[0]][j[1]] = temp;
        plan += "" + a[i[0]][i[1]] + "<->" + a[j[0]][j[1]] + "\n";
    }

    private String plan="";

    private void quickSorter2DExecute(int[] l,int[] r){
        int right = r[0]*column+r[1];
        int left = l[0]*column+l[1];
        if(right-left<1){
            return;
        }
        else{
            int pointer = left;
            int aim=right;
            while(pointer!=aim){
                if(pointer<aim){
                    if(a[pointer/column][pointer%column].compareTo(a[aim/column][aim%column])>0){
                        int[] args1={pointer/column,pointer%column};
                        int[] args2={aim/column,aim%column};
                        swap(args1, args2);
                        int temp=pointer;
                        pointer=aim;
                        aim=temp;
                        aim++;
                    }
                    else{
                        aim--;
                    }
                }
                else{
                    if(a[pointer/column][pointer%column].compareTo(a[aim/column][aim%column])<0){
                        int[] args1={pointer/column,pointer%column};
                        int[] args2={aim/column,aim%column};
                        swap(args1, args2);
                        int temp=pointer;
                        pointer=aim;
                        aim=temp;
                        aim--;
                    }
                    else{
                        aim++;
                    }
                }
            }
            int[] args1={left/column,left%column};
            int[] args2={(pointer-1)/column,(pointer-1)%column};
            int[] args3={(pointer+1)/column,(pointer+1)%column};
            int[] args4={right/column,right%column};
            quickSorter2DExecute(args1, args2);
            quickSorter2DExecute(args3, args4);
        }
    }

    @Override
    public String getPlan() {
        return this.plan;
    }
    
}
