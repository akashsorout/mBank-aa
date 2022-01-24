package in.co.bytehub.mbankaa.response;


public class WalletBalanceResponse extends SuccessResponse {
    private Double balance;

    public WalletBalanceResponse(String userName) {
        super(userName);
    }

    public WalletBalanceResponse(String userName, Double balance) {
        super(userName);
        this.balance = balance;
    }

    public Double getBalance() {
        return balance;
    }

    public WalletBalanceResponse setBalance(Double balance) {
        this.balance = balance;
        return this;
    }
}
