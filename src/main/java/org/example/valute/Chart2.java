package org.example.valute;

import java.awt.Color;
import java.awt.Dimension;
import java.sql.*;

import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.StandardChartTheme;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.block.BlockBorder;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.ui.ApplicationFrame;
import org.jfree.chart.ui.RectangleInsets;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;


public class Chart2 extends ApplicationFrame {

    private final Dao dao = new Dao();
    private final Connection connection = dao.getConnection();

    static {
        ChartFactory.setChartTheme(new StandardChartTheme("JFree/Shadow", true));
    }

    public Chart2(String title) throws SQLException {
        super(title);
        setContentPane(createDemoPanel());
    }
    public JPanel createDemoPanel() {
        CategoryDataset dataset = createDataset();
        JFreeChart chart = createChart(dataset);
        chart.setPadding(new RectangleInsets(4, 8, 2, 2));
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setFillZoomRectangle(true);
        chartPanel.setMouseWheelEnabled(true);
        chartPanel.setPreferredSize(new Dimension(1000, 600));
        return chartPanel;
    }

    private CategoryDataset createDataset() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        try( Statement statement = connection.createStatement()) {

        String sql = "select \"USD\" , \"EUR\" , \"CNY\" , \"JPY\" from valutes ORDER BY id DESC LIMIT 4";
        ResultSet set = statement.executeQuery(sql);


            set.next();
            dataset.addValue(set.getDouble("USD"), "USD", "USD");
            dataset.addValue(set.getDouble("EUR"), "EUR", "USD");
            dataset.addValue(set.getDouble("CNY"), "CNY (10)", "USD");
            dataset.addValue(set.getDouble("JPY"), "JPY (100)", "USD");
            set.next();
            dataset.addValue(set.getDouble("USD"), "USD", "EUR");
            dataset.addValue(set.getDouble("EUR"), "EUR", "EUR");
            dataset.addValue(set.getDouble("CNY"), "CNY (10)", "EUR");
            dataset.addValue(set.getDouble("JPY"), "JPY (100)", "EUR");
            set.next();
            dataset.addValue(set.getDouble("USD"), "USD", "CNY");
            dataset.addValue(set.getDouble("EUR"), "EUR", "CNY");
            dataset.addValue(set.getDouble("CNY"), "CNY (10)", "CNY");
            dataset.addValue(set.getDouble("JPY"), "JPY (100)", "CNY");
            set.next();
            dataset.addValue(set.getDouble("USD"), "USD", "JPY");
            dataset.addValue(set.getDouble("EUR"), "EUR", "JPY");
            dataset.addValue(set.getDouble("CNY"), "CNY (10)", "JPY");
            dataset.addValue(set.getDouble("JPY"), "JPY (100)", "JPY");


        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return dataset;
    }

    private JFreeChart createChart(CategoryDataset dataset) {
        JFreeChart chart = ChartFactory.createBarChart(
                "Course of Valutes",
                null,
                "Ratio",
                dataset);
//        chart.addSubtitle(new TextTitle("В доходе включен только заработок по основной работе"));
        chart.setBackgroundPaint(Color.white);
        CategoryPlot plot = (CategoryPlot) chart.getPlot();

        NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        BarRenderer renderer = (BarRenderer) plot.getRenderer();
        renderer.setDrawBarOutline(false);
        chart.getLegend().setFrame(BlockBorder.NONE);
        return chart;
    }

    public static void main(String[] args) throws SQLException {
        Chart2 demo = new Chart2("Valute");

        demo.pack();

        demo.setVisible(true);
    }
}
