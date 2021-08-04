package client.model;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * @author Zhang Hao
 *
 * 该类实现战绩饼状图的绘制，处于简化路径考虑，将生成图片存储在D：\下，生成图片以当前时间命名.
 * */
public class Chart {

    /**
     * @return String
     * @param win
     * @param lose
     *
     * 该方法通过传入获胜和失败的数字，生成饼状图，返回图片存储的路径.
     * */
    public String savePieChart(int win, int lose){
        //利用当前时间生成图片文件名
        Date day=new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
        String pictureName = df.format(day);
        try {
            //如果不使用Font,中文将显示不出来
            Font font = new Font("宋体", Font.BOLD, 15);

            DefaultPieDataset pds = new DefaultPieDataset();
            pds.setValue("win", win);
            pds.setValue("lose", lose);

            /**
             * 生成一个饼图的图表
             * 分别是:显示图表的标题、需要提供对应图表的DateSet对象、是否显示图例、是否生成贴士以及是否生成URL链接
             */
            JFreeChart chart = ChartFactory.createPieChart("Military Success", pds, true, false, true);
            //设置图片标题的字体
            chart.getTitle().setFont(font);

            //得到图块,准备设置标签的字体
            PiePlot plot = (PiePlot) chart.getPlot();

            //设置分裂效果,需要指定分裂出去的key
            plot.setExplodePercent("win", 0.3);

            //设置标签字体
            plot.setLabelFont(font);

            //设置图例项目字体
            chart.getLegend().setItemFont(font);

            /**
             * 设置开始角度(弧度计算)
             *
             * 度    0°    30°        45°        60°        90°        120°    135°    150°    180°    270°    360°
             * 弧度    0    π/6        π/4        π/3        π/2        2π/3    3π/4    5π/6    π        3π/2    2π
             */
            plot.setStartAngle(3.14f / 2f);

            //设置背景图片,设置最大的背景
            Image img = ImageIO.read(new File("d:/background.jpg"));
            chart.setBackgroundImage(img);

            //设置plot的背景图片
            img = ImageIO.read(new File("d:/plotBack.jpg"));
            plot.setBackgroundImage(img);

            //设置plot的前景色透明度
            plot.setForegroundAlpha(0.7f);

            //设置plot的背景色透明度
            plot.setBackgroundAlpha(0.0f);

            //设置标签生成器(默认{0})
            //{0}:key {1}:value {2}:百分比 {3}:sum
            plot.setLabelGenerator(new StandardPieSectionLabelGenerator("{0}({1})/{2}"));

            //将内存中的图片写到本地硬盘
            ChartUtils.saveChartAsJPEG(new File("d:/"+pictureName+".jpg"), chart, 400, 300);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "d:\\"+pictureName+".jpg";
    }
}