package com.greenit.greenitapi.Entities;

public class Step {
    private String id;
    private Step previousStep;
    private String description;
    //private String image;
    //private List<Comment> comments;

    /*
    La id es un string con un formato muy concreto:
    idPost-nombreServerPost-AutorOriginal-nºpaso-autorPaso-nombreServerPaso

    Sean Server 1 = Aethereius y Server 2 = Letelier
    Usuario 1 = Patricio y Usuario 2 = Lluis
    Y sea la estructura de un post cualquiera la siguiente:

    1-2-3-4a-5a
        \-4b-5b-6b

    Donde 4b-5b-6b están en Letelier y 1-2-3-4a-5a están en Aetherius
    El post fue publicado originalmente por Patricio
    Y la rama 4b-5b-6b fue publicada por Lluis

    La id de 1 es:
    42069-Aetherius-Patricio-1-Patricio-Aetherius

    La id de 4a es:
    42069-Aetherius-Patricio-4-Patricio-Aetherius

    La id de 4b es:
    42069-Aetherius-Patricio-4-Lluis-Letelier


    Partiendo de un post cualquiera, como es el 42069 desde Aetherius,
     buscamos pasos en el resto de servidores que tengan su id tal que:

     42069-Aetherius-Patricio-X-Y-Z
     Donde X es el paso, que siendo saliendo del post pues 1, o si partimos de un paso pues es numero
      en el que estás +1 o algo no se ya lo pienso luego

      Y y Z nos importan poco porque serán el autor del "commit" y el server donde reside el paso
      Z es más importante si no devolvemos el paso con la primera consulta pq tendremos que pedir el paso por id pero weno



                                    FAQ
      Q:pq no solo la id? pq usamos el nombre de server y posteador ?
      A:pq asi no tenemos que coordinar ids y es mas sencillo tmb para hacer backtracking desde otros pasos
    */
}
