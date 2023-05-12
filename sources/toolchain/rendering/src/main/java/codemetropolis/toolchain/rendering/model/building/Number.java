package codemetropolis.toolchain.rendering.model.building;

import java.util.LinkedList;
import codemetropolis.toolchain.commons.cmxml.Buildable;
import codemetropolis.toolchain.commons.cmxml.Point;
import codemetropolis.toolchain.rendering.model.BasicBlock;
import codemetropolis.toolchain.rendering.model.pattern.RepeationPattern;
import codemetropolis.toolchain.rendering.model.primitive.*;
import codemetropolis.toolchain.rendering.util.Orientation;



/**
 * Created by Gábor on 2017. 04. 03..
 */
public class Number extends Building{


    protected int GardenPositionX;
    protected int GardenPositionZ;
    protected int GardenPositionY;
    protected int numberToBuild;
    protected int positionZ;



    public Number(Buildable Garden,String metricToBuild){
         super(Garden);

        GardenPositionX=Garden.getPositionX();
        GardenPositionZ=Garden.getPositionZ();
        GardenPositionY=Garden.getPositionY();


        switch(metricToBuild){
            case "BuiltMetric1":
                numberToBuild=Garden.getBuiltMetric1();
                positionZ=GardenPositionZ +1;
                Point posBuiltMetric1=new Point(GardenPositionX,GardenPositionY+4,positionZ);
                setPosition(posBuiltMetric1);
                break;
            case "BuiltMetric2":
                numberToBuild=Garden.getBuiltMetric2();
                positionZ=GardenPositionZ +6;
                Point posBuiltMetric2=new Point(GardenPositionX,GardenPositionY+4,positionZ);
                setPosition(posBuiltMetric2);
                break;
            case "BuiltMetric3":
                numberToBuild=Garden.getBuiltMetric3();
                positionZ=GardenPositionZ +11;
                Point posBuiltMetric3=new Point(GardenPositionX,GardenPositionY+4,positionZ);
                setPosition(posBuiltMetric3);
                break;

        }
        prepareNumber(numberToBuild,positionZ);
    }

    protected LinkedList<Primitive> prepareNumber(int number,int positionZ){
        LinkedList<Primitive> numbers = new LinkedList<>();
        BasicBlock _air = BasicBlock.AIR;
        BasicBlock _str = BasicBlock.STONE;

        if(number==0){
            numbers.add(
                    new SolidBox(
                            new Point( GardenPositionX - 1, GardenPositionY+4, positionZ ) ,
                            new Point( 3, 5, 5 ),

                            new RepeationPattern(
                                    new BasicBlock[][][]
                                            {
                                                    {

                                                            { _air,_air, _air, _air,_air },
                                                            { _air,_str, _str, _str,_air },
                                                            { _air,_air, _air, _air,_air }




                                                    },
                                                    {

                                                            { _air,_air, _air, _air,_air },
                                                            { _air,_str, _air, _str,_air },
                                                            { _air,_air, _air, _air,_air }


                                                    },
                                                    {

                                                            { _air,_air, _air, _air,_air },
                                                            { _air,_str, _air, _str,_air },
                                                            { _air,_air, _air, _air,_air }

                                                    },
                                                    {

                                                            { _air,_air, _air, _air,_air },
                                                            { _air,_str, _air, _str,_air },
                                                            { _air,_air, _air, _air,_air }


                                                    },
                                                    {

                                                            { _air,_air, _air, _air,_air },
                                                            { _air,_str, _str, _str,_air},
                                                            { _air,_air, _air, _air,_air }


                                                    }

                                            } ),
                            new RepeationPattern( new BasicBlock[][][] {{{BasicBlock.AIR}}} ),

                            Orientation.NearY )
            );

        }
        int tmp;
        int PositionShift=0;
        while(number>0)
       {

            tmp=number%10;
           number=number/10;

            switch(tmp) {
                case 9:
                    numbers.add(
                            new SolidBox(
                                    new Point( GardenPositionX - 1, GardenPositionY+4+PositionShift, positionZ ) ,
                                    new Point( 3, 5, 5 ),

                                    new RepeationPattern(
                                            new BasicBlock[][][]
                                                    {
                                                            {

                                                                    { _air,_air, _air, _air,_air },
                                                                    { _air,_str, _str, _str,_air },
                                                                    { _air,_air, _air, _air,_air }




                                                            },
                                                            {

                                                                    { _air,_air, _air, _air,_air },
                                                                    { _air,_str, _air, _str,_air },
                                                                    { _air,_air, _air, _air,_air }


                                                            },
                                                            {

                                                                    { _air,_air, _air, _air,_air },
                                                                    { _air,_str, _str, _str,_air },
                                                                    { _air,_air, _air, _air,_air }

                                                            },
                                                            {

                                                                    { _air,_air, _air, _air,_air },
                                                                    { _air,_str, _air, _air,_air },
                                                                    { _air,_air, _air, _air,_air }


                                                            },
                                                            {

                                                                    { _air,_air, _air, _air,_air },
                                                                    { _air,_str, _str, _str,_air},
                                                                    { _air,_air, _air, _air,_air }


                                                            }

                                                    } ),
                                    new RepeationPattern( new BasicBlock[][][] {{{BasicBlock.AIR}}} ),

                                    Orientation.NearY )
                    );
                    break;

                case 8:
                    numbers.add(
                            new SolidBox(
                                    new Point( GardenPositionX - 1, GardenPositionY+4+PositionShift, positionZ ) ,
                                    new Point( 3, 5, 5 ),

                                    new RepeationPattern(
                                            new BasicBlock[][][]
                                                    {
                                                            {

                                                                    { _air,_air, _air, _air,_air },
                                                                    { _air,_str, _str, _str,_air },
                                                                    { _air,_air, _air, _air,_air }




                                                            },
                                                            {

                                                                    { _air,_air, _air, _air,_air },
                                                                    { _air,_str, _air, _str,_air },
                                                                    { _air,_air, _air, _air,_air }


                                                            },
                                                            {

                                                                    { _air,_air, _air, _air,_air },
                                                                    { _air,_str, _str, _str,_air },
                                                                    { _air,_air, _air, _air,_air }

                                                            },
                                                            {

                                                                    { _air,_air, _air, _air,_air },
                                                                    { _air,_str, _air, _str,_air },
                                                                    { _air,_air, _air, _air,_air }


                                                            },
                                                            {

                                                                    { _air,_air, _air, _air,_air },
                                                                    { _air,_str, _str, _str,_air},
                                                                    { _air,_air, _air, _air,_air }


                                                            }

                                                    } ),
                                    new RepeationPattern( new BasicBlock[][][] {{{BasicBlock.AIR}}} ),

                                    Orientation.NearY )
                    );
                    break;

                case 7:
                    numbers.add(
                            new SolidBox(
                                    new Point( GardenPositionX - 1, GardenPositionY+4+PositionShift, positionZ ) ,
                                    new Point( 3, 5, 5 ),

                                    new RepeationPattern(
                                            new BasicBlock[][][]
                                                    {
                                                            {

                                                                    { _air,_air, _air, _air,_air },
                                                                    { _air,_str, _str, _str,_air },
                                                                    { _air,_air, _air, _air,_air }




                                                            },
                                                            {

                                                                    { _air,_air, _air, _air,_air },
                                                                    { _air,_str, _air, _air,_air },
                                                                    { _air,_air, _air, _air,_air }


                                                            },
                                                            {

                                                                    { _air,_air, _air, _air,_air },
                                                                    { _air,_str, _air, _air,_air },
                                                                    { _air,_air, _air, _air,_air }

                                                            },
                                                            {

                                                                    { _air,_air, _air, _air,_air },
                                                                    { _air,_str, _air, _air,_air },
                                                                    { _air,_air, _air, _air,_air }


                                                            },
                                                            {

                                                                    { _air,_air, _air, _air,_air },
                                                                    { _air,_str, _air, _air,_air},
                                                                    { _air,_air, _air, _air,_air }


                                                            }

                                                    } ),
                                    new RepeationPattern( new BasicBlock[][][] {{{BasicBlock.AIR}}} ),

                                    Orientation.NearY )
                    );
                    break;
                case 6:
                    numbers.add(
                            new SolidBox(
                                    new Point( GardenPositionX - 1, GardenPositionY+4+PositionShift, positionZ ) ,
                                    new Point( 3, 5, 5 ),

                                    new RepeationPattern(
                                            new BasicBlock[][][]
                                                    {
                                                            {

                                                                    { _air,_air, _air, _air,_air },
                                                                    { _air,_str, _str, _str,_air },
                                                                    { _air,_air, _air, _air,_air }




                                                            },
                                                            {

                                                                    { _air,_air, _air, _air,_air },
                                                                    { _air,_air, _air, _str,_air },
                                                                    { _air,_air, _air, _air,_air }


                                                            },
                                                            {

                                                                    { _air,_air, _air, _air,_air },
                                                                    { _air,_str, _str, _str,_air },
                                                                    { _air,_air, _air, _air,_air }

                                                            },
                                                            {

                                                                    { _air,_air, _air, _air,_air },
                                                                    { _air,_str, _air, _str,_air },
                                                                    { _air,_air, _air, _air,_air }


                                                            },
                                                            {

                                                                    { _air,_air, _air, _air,_air },
                                                                    { _air,_str, _str, _str,_air},
                                                                    { _air,_air, _air, _air,_air }


                                                            }

                                                    } ),
                                    new RepeationPattern( new BasicBlock[][][] {{{BasicBlock.AIR}}} ),

                                    Orientation.NearY )
                    );
                    break;
                case 5:
                    numbers.add(
                            new SolidBox(
                                    new Point( GardenPositionX - 1, GardenPositionY+4+PositionShift, positionZ ) ,
                                    new Point( 3, 5, 5 ),

                                    new RepeationPattern(
                                            new BasicBlock[][][]
                                                    {
                                                            {

                                                                    { _air,_air, _air, _air,_air },
                                                                    { _air,_str, _str, _str,_air },
                                                                    { _air,_air, _air, _air,_air }




                                                            },
                                                            {

                                                                    { _air,_air, _air, _air,_air },
                                                                    { _air,_air, _air, _str,_air },
                                                                    { _air,_air, _air, _air,_air }


                                                            },
                                                            {

                                                                    { _air,_air, _air, _air,_air },
                                                                    { _air,_str, _str, _str,_air },
                                                                    { _air,_air, _air, _air,_air }

                                                            },
                                                            {

                                                                    { _air,_air, _air, _air,_air },
                                                                    { _air,_str, _air, _air,_air },
                                                                    { _air,_air, _air, _air,_air }


                                                            },
                                                            {

                                                                    { _air,_air, _air, _air,_air },
                                                                    { _air,_str, _str, _str,_air},
                                                                    { _air,_air, _air, _air,_air }


                                                            }

                                                    } ),
                                    new RepeationPattern( new BasicBlock[][][] {{{BasicBlock.AIR}}} ),

                                    Orientation.NearY )
                    );
                    break;
                case 4:
                    numbers.add(
                            new SolidBox(
                                    new Point( GardenPositionX - 1, GardenPositionY+4+PositionShift, positionZ ) ,
                                    new Point( 3, 5, 5 ),

                                    new RepeationPattern(
                                            new BasicBlock[][][]
                                                    {
                                                            {

                                                                    { _air,_air, _air, _air,_air },
                                                                    { _air,_air, _air, _str,_air },
                                                                    { _air,_air, _air, _air,_air }




                                                            },
                                                            {

                                                                    { _air,_air, _air, _air,_air },
                                                                    { _air,_air, _air, _str,_air },
                                                                    { _air,_air, _air, _air,_air }


                                                            },
                                                            {

                                                                    { _air,_air, _air, _air,_air },
                                                                    { _air,_air, _air, _str,_air },
                                                                    { _air,_air, _air, _air,_air }

                                                            },
                                                            {

                                                                    { _air,_air, _air, _air,_air },
                                                                    { _air,_str, _str, _str,_air },
                                                                    { _air,_air, _air, _air,_air }


                                                            },
                                                            {

                                                                    { _air,_air, _air, _air,_air },
                                                                    { _air,_air, _str, _air,_air},
                                                                    { _air,_air, _air, _air,_air }


                                                            }

                                                    } ),
                                    new RepeationPattern( new BasicBlock[][][] {{{BasicBlock.AIR}}} ),

                                    Orientation.NearY )
                    );
                    break;
                case 3:
                    numbers.add(
                            new SolidBox(
                                    new Point( GardenPositionX - 1, GardenPositionY+4+PositionShift, positionZ ) ,
                                    new Point( 3, 5, 5 ),

                                    new RepeationPattern(
                                            new BasicBlock[][][]
                                                    {
                                                            {

                                                                    { _air,_air, _air, _air,_air },
                                                                    { _air,_str, _str, _str,_air },
                                                                    { _air,_air, _air, _air,_air }




                                                            },
                                                            {

                                                                    { _air,_air, _air, _air,_air },
                                                                    { _air,_str, _air, _air,_air },
                                                                    { _air,_air, _air, _air,_air }


                                                            },
                                                            {

                                                                    { _air,_air, _air, _air,_air },
                                                                    { _air,_str, _str, _air,_air },
                                                                    { _air,_air, _air, _air,_air }

                                                            },
                                                            {

                                                                    { _air,_air, _air, _air,_air },
                                                                    { _air,_str, _air, _air,_air },
                                                                    { _air,_air, _air, _air,_air }


                                                            },
                                                            {

                                                                    { _air,_air, _air, _air,_air },
                                                                    { _air,_str, _str, _str,_air},
                                                                    { _air,_air, _air, _air,_air }


                                                            }

                                                    } ),
                                    new RepeationPattern( new BasicBlock[][][] {{{BasicBlock.AIR}}} ),

                                    Orientation.NearY )
                    );
                    break;
                case 2:
                    numbers.add(
                            new SolidBox(
                                    new Point( GardenPositionX - 1, GardenPositionY+4+PositionShift, positionZ ) ,
                                    new Point( 3, 5, 5 ),

                                    new RepeationPattern(
                                            new BasicBlock[][][]
                                                    {
                                                            {

                                                                    { _air,_air, _air, _air,_air },
                                                                    { _air,_str, _str, _str,_air },
                                                                    { _air,_air, _air, _air,_air }




                                                            },
                                                            {

                                                                    { _air,_air, _air, _air,_air },
                                                                    { _air,_str, _air, _air,_air },
                                                                    { _air,_air, _air, _air,_air }


                                                            },
                                                            {

                                                                    { _air,_air, _air, _air,_air },
                                                                    { _air,_str, _str, _str,_air },
                                                                    { _air,_air, _air, _air,_air }

                                                            },
                                                            {

                                                                    { _air,_air, _air, _air,_air },
                                                                    { _air,_air, _air, _str,_air },
                                                                    { _air,_air, _air, _air,_air }


                                                            },
                                                            {

                                                                    { _air,_air, _air, _air,_air },
                                                                    { _air,_str, _str, _str,_air},
                                                                    { _air,_air, _air, _air,_air }


                                                            }

                                                    } ),
                                    new RepeationPattern( new BasicBlock[][][] {{{BasicBlock.AIR}}} ),

                                    Orientation.NearY )
                    );
                    break;
                case 1:
                    numbers.add(
                            new SolidBox(
                                    new Point( GardenPositionX - 1, GardenPositionY+4+PositionShift, positionZ ) ,
                                    new Point( 3, 5, 5 ),

                                    new RepeationPattern(
                                            new BasicBlock[][][]
                                                    {
                                                            {

                                                                    { _air,_air, _air, _air,_air },
                                                                    { _air,_air, _str, _str,_air },
                                                                    { _air,_air, _air, _air,_air }




                                                            },
                                                            {

                                                                    { _air,_air, _air, _air,_air },
                                                                    { _air,_air, _str, _air,_air },
                                                                    { _air,_air, _air, _air,_air }


                                                            },
                                                            {

                                                                    { _air,_air, _air, _air,_air },
                                                                    { _air,_air, _str, _air,_air },
                                                                    { _air,_air, _air, _air,_air }

                                                            },
                                                            {

                                                                    { _air,_air, _air, _air,_air },
                                                                    { _air,_air, _str, _air,_air },
                                                                    { _air,_air, _air, _air,_air }


                                                            },
                                                            {

                                                                    { _air,_air, _air, _air,_air },
                                                                    { _air,_str, _str, _str,_air},
                                                                    { _air,_air, _air, _air,_air }


                                                            }

                                                    } ),
                                    new RepeationPattern( new BasicBlock[][][] {{{BasicBlock.AIR}}} ),

                                    Orientation.NearY )
                    );
                    break;
                case 0:
                    numbers.add(
                            new SolidBox(
                                    new Point( GardenPositionX - 1, GardenPositionY+4+PositionShift, positionZ ) ,
                                    new Point( 3, 5, 5 ),

                                    new RepeationPattern(
                                            new BasicBlock[][][]
                                                    {
                                                            {

                                                                    { _air,_air, _air, _air,_air },
                                                                    { _air,_str, _str, _str,_air },
                                                                    { _air,_air, _air, _air,_air }




                                                            },
                                                            {

                                                                    { _air,_air, _air, _air,_air },
                                                                    { _air,_str, _air, _str,_air },
                                                                    { _air,_air, _air, _air,_air }


                                                            },
                                                            {

                                                                    { _air,_air, _air, _air,_air },
                                                                    { _air,_str, _air, _str,_air },
                                                                    { _air,_air, _air, _air,_air }

                                                            },
                                                            {

                                                                    { _air,_air, _air, _air,_air },
                                                                    { _air,_str, _air, _str,_air },
                                                                    { _air,_air, _air, _air,_air }


                                                            },
                                                            {

                                                                    { _air,_air, _air, _air,_air },
                                                                    { _air,_str, _str, _str,_air},
                                                                    { _air,_air, _air, _air,_air }


                                                            }

                                                    } ),
                                    new RepeationPattern( new BasicBlock[][][] {{{BasicBlock.AIR}}} ),

                                    Orientation.NearY )
                    );
                    break;
            }
            PositionShift=PositionShift+6;

        }
        return numbers;
    }




}