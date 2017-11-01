package com.example.jdbcdemo.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import com.example.jdbcdemo.domain.Computer;

public class ComputerManagerJDBC implements ComputerManager {
	
	private Connection connection;
	private String url = "jdbc:hsqldb:hsql://localhost/workdb";
	private String createComputerTable = "CREATE TABLE Computer (id bigint GENERATED BY DEFAULT AS IDENTITY, model varchar(20), ram bigint,"
			+ " cpu varchar(20), hdd bigint, gpu varchar(50), price DECIMAL (6,2))";
	private Statement statement;
	
	private PreparedStatement addComputerStmt;
	private PreparedStatement deleteAllComputersStmt;
	private PreparedStatement getAllComputersStmt;

	public ComputerManagerJDBC() {
		try {
			connection = DriverManager.getConnection(url);
			statement = connection.createStatement();

			ResultSet rs = connection.getMetaData().getTables(null, null, null,
					null);
			boolean tableExists = false;
			while (rs.next()) {
				if ("Computer".equalsIgnoreCase(rs.getString("TABLE_NAME"))) {
					tableExists = true;
					break;
				}
			}

			if (!tableExists)
				statement.executeUpdate(createComputerTable);

			addComputerStmt = connection
					.prepareStatement("INSERT INTO Computer(model, ram, cpu, hdd, gpu, price) VALUES (?, ?, ?, ?, ?, ?)");
			deleteAllComputersStmt = connection
					.prepareStatement("DELETE FROM Computer");
			getAllComputersStmt = connection
					.prepareStatement("SELECT model, ram, cpu, hdd, gpu, price FROM Computer");

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public int addComputer(Computer computer) {

		int count = 0;
		try {

			addComputerStmt.setString(1, computer.getModel());
			addComputerStmt.setInt(2, computer.getRam());
			addComputerStmt.setString(3, computer.getCpu());
			addComputerStmt.setInt(4, computer.getHdd());
			addComputerStmt.setString(5, computer.getGpu());
			addComputerStmt.setDouble(6, computer.getPrice());
			count = addComputerStmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return count;
	}

	@Override
	public List<Computer> getAllComputers() {
		// TODO Auto-generated method stub
		return null;
	}

	public void deleteComputer(int id) {

	}

	public void updateComputer(int id) {

	}
	Connection getConnection() {
		return this.connection;
	}



}
