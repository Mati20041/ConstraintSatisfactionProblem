package com.lds.mati.CSP.GUI;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JOptionPane;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.BoxLayout;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;


import com.lds.mati.CSP.HetmanProblem.HetmansProblem;
import com.lds.mati.CSP.engine.CSPSolver;
import com.lds.mati.CSP.engine.Coinstraint;
import com.lds.mati.CSP.engine.ConstraintsProblem;
import com.lds.mati.CSP.engine.Hook;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.List;
import javax.swing.JCheckBox;

public class GUI extends JFrame implements Hook<Integer>{

	private JPanel contentPane;
	private JTextField textField;
	private ChessBoard chess;
	private int boardSize;
	private JButton btnNewButton;
	private JCheckBox chckbxPodgld;
	/**
	 * @wbp.nonvisual location=456,429
	 */
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JRadioButton rdbtnNewRadioButton;


	/**
	 * Create the frame.
	 */
	public GUI() {
		setTitle("Problem Hetmanów");
		initGui();
		
	}

	private void initGui() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 646, 467);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.SOUTH);
		
		chckbxPodgld = new JCheckBox("Podgl\u0105d");
		panel.add(chckbxPodgld);
		
		JLabel lblNewLabel = new JLabel("Liczba hetmanów");
		panel.add(lblNewLabel);
		
		textField = new JTextField();
		textField.setText("8");
		panel.add(textField);
		textField.setColumns(10);
		
		btnNewButton = new JButton("Rozmieœæ");
		
		panel.add(btnNewButton);
		
		JPanel panel_1 = new JPanel();
		panel.add(panel_1);
		panel_1.setLayout(new BoxLayout(panel_1, BoxLayout.Y_AXIS));
		
		rdbtnNewRadioButton = new JRadioButton("backtracking");
		rdbtnNewRadioButton.setSelected(true);
		panel_1.add(rdbtnNewRadioButton);
		
		JRadioButton rdbtnNewRadioButton_1 = new JRadioButton("forwardchecking");
		panel_1.add(rdbtnNewRadioButton_1);
		buttonGroup.add(rdbtnNewRadioButton);
		buttonGroup.add(rdbtnNewRadioButton_1);
		
		JPanel panel_2 = new JPanel();
		contentPane.add(panel_2, BorderLayout.CENTER);
		panel_2.setLayout(new BorderLayout(0, 0));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				buttonPressed();
			}
		});
		chess = new ChessBoard();
		panel_2.add(chess);
	}


	private void buttonPressed() {
		boardSize = 0;
		try {
			boardSize = Integer.parseInt(textField.getText());
			if(boardSize<1)
				throw new NumberFormatException();
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(this, "Wprowadzona wartoœæ jest niepoprawna!");
			return;
		}
		btnNewButton.setEnabled(false);
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				List<Integer> result = runScript(rdbtnNewRadioButton.isSelected(), chckbxPodgld.isSelected(), boardSize);
				if(result!=null)
					chess.setPositions(result);
				else
					JOptionPane.showMessageDialog(GUI.this, "Brak rozwi¹zania!");
				btnNewButton.setEnabled(true);
			}
		}).start();
	}

	private List<Integer> runScript(boolean backtracking,boolean view,int boardSize) {
		HetmansProblem factory = HetmansProblem.getFactory();
		List<Coinstraint<Integer>> coinstraints = factory.getCoinstraints(boardSize);
		List<List<Integer>> domains = factory.getDomain(boardSize);
		
		ConstraintsProblem<Integer> problem = new ConstraintsProblem<Integer>(boardSize, coinstraints, domains);
		CSPSolver<Integer> solver = new CSPSolver<>();
		long currentTime = System.currentTimeMillis();
		List<Integer> result;
		if(backtracking)
			if(view)
				result =  solver.backTracking(problem,this);
			else
				result =  solver.backTracking(problem);
		else 
			if(view)
				result =  solver.forwardChecking(problem,this);
			else
				result =  solver.forwardChecking(problem);
		System.out.println("Czas dzia³ania "+(System.currentTimeMillis()-currentTime)+" ms");
		return result;
	}

	@Override
	public void partialResult(List<Integer> result) {
		chess.setPositions(result);
	}

}
