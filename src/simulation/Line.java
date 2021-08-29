/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simulation;

import Jama.Matrix;
import java.util.ArrayList;
import static simulation.Simulation.random;

/**
 *
 * @author Wang
 */
public class Line extends Shape {
        
        double deg;
        double v;
        double a;
        long time;
        double fps;
        
        public Line(double deg, double v, double a, long time, double fps) {
            this.deg = deg;
            this.v = v;
            this.a = a;
            this.time = time;
            this.fps = fps;
        }

        /**
         * 生成匀速直线运动的状态序列
         *
         * @return
         * 描述运动状态的序列，序列的每个元素是一个5*1的列向量，五个元素分别代表时间、X方向位置、Y方向位置、X方向速度、Y方向速度，单位为ms、cm和cm/s
         */
        @Override
        public ArrayList<StateStamp> generate() {
            ArrayList<StateStamp> statelist = new ArrayList<>();
            double x = random.nextDouble(30, 80);
            double y = random.nextDouble(30, 80);
            double vv = v;
            double rad = deg / 180.0 * Math.PI;//角度转为弧度
            double deltaT = 1000.0/fps;
            for (long t = 0; t <= time; t += deltaT) {
                double mat[][] = {{x}, {y}, {vv * Math.cos(rad)}, {vv * Math.sin(rad)}, {a * Math.cos(rad)}, {a * Math.sin(rad)}};
                statelist.add(new StateStamp(t, new Matrix(mat), null));
                x += (v * Math.cos(rad) *deltaT)/1000.0+(a*Math.cos(rad)*(2*deltaT*t+deltaT*deltaT))/2000000.0;
                y += (v * Math.sin(rad) *deltaT)/1000.0+(a*Math.sin(rad)*(2*deltaT*t+deltaT*deltaT))/2000000.0;
                vv += a*deltaT/1000.0;
            }
            return statelist;
        }
    }
