
public enum Mark{
        X,
        O,
        EMPTY;

    public Mark enemy(){
            if (this == Mark.O){
                return Mark.X;
            }else{
                return Mark.O;
            }
    }
}


