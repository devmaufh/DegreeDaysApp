package com.devmaufh.degreedaysapp.Utilities;/*

 * The MIT License
         *
         * Copyright 2019 Mauricio Flores Hernandez.
         *
         * Permission is hereby granted, free of charge, to any person obtaining a copy
         * of this software and associated documentation files (the "Software"), to deal
         * in the Software without restriction, including without limitation the rights
         * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
         * copies of the Software, and to permit persons to whom the Software is
         * furnished to do so, subject to the following conditions:
         *
         * The above copyright notice and this permission notice shall be included in
         * all copies or substantial portions of the Software.
         *
         * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
         * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
         * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
         * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
         * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
         * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
         * THE SOFTWARE.
         */

/**
 *
 * @author Mauricio Flores Hernández
 * @github https://github.com/devmaufh
 * @version 0.1
 * @description It is an open source library for calculating grade days using the simple sine method
 *
 *
 */
public class ITRDegreeDays {
    //Atributos
    private double TU,TL,tMax,tMin;
    private int CASE=0;

    /**
     *Constructur
     * @param TU umbral superior, debe ser un valor double
     * @param TL umbral inferior, debe ser un valor double
     * @param tMax temperatura máxima, debe ser un valor double
     * @param tMin temperatura mínima, debe ser un valor double
     *
     * Al ejecutarse el constructur, se asigna el caso correspondiente de acuerdo a los umbrales y temperaturas
     */
    public ITRDegreeDays(double TU, double TL, double tMax, double tMin) {
        this.TU = TU;
        this.TL = TL;
        this.tMax = tMax;
        this.tMin = tMin;
        CASE=this.define_case();
    }

    /**
     * Devuelve un entero con el número del caso correspondiente
     * @return integer
     */
    private int define_case(){
        if(this.tMax>this.tMin){
            if(this.tMin>this.TU)
                return 5;
            if(this.TL>this.tMax && this.tMin<this.TL)
                return 1;
            if(this.TL>this.tMin && this.TU>this.tMax)
                return 2;
            if(this.TU>this.tMax&& this.TL <this.tMin)
                return 3;
            if(this.TU<this.tMax && this.tMin>this.TL)
                return 4;
            if(this.TL>this.tMin && this.tMax>this.TU)
                return 6;
            return 0;
        }else{
            return 0;
        }
    }

    /**
     * Resuelve el caso en base al atributo CASE que se asignó
     * @return void
     */
    public double solve(){
        double r=0;
        switch(CASE){
            case 0: r= -1;break;
            case 1: r=case_1();break;
            case 2: r=case_2();break;
            case 3:r=case_3();break;
            case 4:r=case_4();break;
            case 5:r=case_5();break;
            case 6:r=case_6();break;
            default: return -1;
        }
        if(r==-1){            return r;

            //throw new IllegalThreadStateException("You have an error in your params");
        }else{
            return r;
        }
    }
    //          Cases

    /**
     * Retorna el valor del caso 1
     * @return double
     */
    private double case_1(){
        return 0;
    }

    /**
     * Retorna el valor del caso 2
     * @return double
     */
    private double case_2(){
        double angle= this.angleAux(this.TL);
        return (1/Math.PI)*((this.case_3()-this.TL)*(Math.PI/2-angle)+this.theta()*Math.cos(angle));

    }

    /**
     * Retorna el valor del caso 3
     * @return double
     */
    private double case_3(){
        return  (this.tMax+this.tMin)/2;
    }

    /**
     * Retorna el valor del caso 4
     * @return double
     */
    private double case_4(){
        double angle=this.angleAux(this.TU);
        return (1/Math.PI)*( (this.case_3()-this.TL)*(this.angleAux(this.TU)+Math.PI/2)+ (this.TU- this.TL)*(Math.PI/2+angle)- (this.theta()*Math.cos(angle)));
    }


    /**
     * Retorna el valor del caso 5
     * @return double
     */
    private double case_5(){
        return this.TU-this.TL;

    }

    /**
     * Retorna el valor del caso 6
     * @return double
     */
    private double case_6(){
        double angle1=this.angleAux(this.TL);
        double angle2=this.angleAux(this.TU);
        return (1/Math.PI)*((this.case_3()-this.TL)*(angle1-angle2)+this.theta()*(Math.cos(angle1)-Math.cos(angle2))+(this.TU-this.TL)*(Math.PI/2 - angle2));


    }
    //          Auxiliar methods

    /**
     * Retorna el valor de theta
     * @return double
     */
    private double theta(){
        return ((this.tMax-this.tMin)/2);
    }

    /**
     * Retorna el valor del angulo theta
     * @param Tn puede ser TU o TL dependiendo del caso
     * @return double
     */
    private double angleAux(double Tn){
        return Math.asin((Tn-this.case_3())/this.theta());
    }
    //          Test Methods

    /**
     * Retorna un String con el caso al que pertenece la instancia
     * @return void
     */
    public void showCase(){
        System.out.println("Case: "+CASE);
    }

    /**
     * Retorna un String con el caso al que pertenece la instancia
     * @return
     */
    @Override
    public String toString() {
        return String.valueOf(CASE);
    }

    /**
     * @Ejemplos
     * ITRDegreeDays case4=new ITRDegreeDays(26, 14, 96, 21);
     * ITRDegreeDays case2= new ITRDegreeDays(26,14,20,10);
     * ITRDegreeDays case6= new ITRDegreeDays(26,14,46,10);
     * ITRDegreeDays errorCase= new ITRDegreeDays(26, 10, 10, 50);
     *
     * System.out.println("Case\tResult");
     * System.out.println(case4+"\t"+case4.solve());
     * //System.out.println(errorCase+"\t"+errorCase.solve());
     * System.out.println(case2+"\t"+case2.solve());
     * System.out.println(case6+"\t"+case6.solve());
     *
     */
}