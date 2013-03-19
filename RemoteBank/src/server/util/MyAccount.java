package server.util;

import bank.Account;
import bank.InactiveException;
import bank.OverdrawException;

public class MyAccount implements Account {
	private final String owner;
	private final String number;
	private double balance;
	private boolean active;
	
	public MyAccount (String owner, String number) {
		this.owner = owner;
		this.number = number;
		this.balance = 0;
		this.active = true;
	}

	@Override
	public String getNumber() {
		return number;
	}

	@Override
	public String getOwner() {
		return owner;
	}

	@Override
	public boolean isActive() {
		return active;
	}
	
	public void setInactive() {
		active = false;
	}

	@Override
	public void deposit(double amount) throws InactiveException {
		synchronized (this) {
			if (!active) throw new InactiveException("unable to withdraw from inactive account " + number);
			if (amount < 0.0) throw new IllegalArgumentException("unable to deposit a amount of " + amount);
			balance += amount;
		}
	}

	@Override
	public void withdraw(double amount) throws OverdrawException, InactiveException {
		synchronized (this) {
			if (balance - amount < 0.0) throw new OverdrawException("unable to withdraw " + amount + " from " + number + " (balance=" + balance + ")");
			if (!active) throw new InactiveException("unable to withdraw from inactive account " + number);
			if (amount < 0.0) throw new IllegalArgumentException("unable to withdraw a amount of " + amount);
			balance -= amount;
		}
	}

	@Override
	public double getBalance() {
		return balance;
	}

}