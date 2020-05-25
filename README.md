# IRMA
Interactive Real-time Measurement of Attention

IRMA (Interactive Real-time Measurement of Attention) is a measuring apparatus designed to investigate performances of audiovisual computer music in situated contexts. It provides devices and software to monitor on which elements or materials the attention of the audience focuses over the course of the performance. The method involves interpretation by collating collected quantitative empirical data alongside qualitative data of questionnaires and interviews as well as musicological analyses. 

Besides being designed as a tool for providing composers and performers in artistic research processes empirical feedback on how audiovisual materials or performative acts are perceived by the audience, IRMA turned out to be able to deliver empirical groundwork for general aesthetical considerations. 

Results on the one hand demonstrate how particular artistic concepts have been perceived by the audience, but on the other hand also indicate general insights on perception of audiovisual compositions. 

IRMA has so far been used and evaluated with 8 computer music performances in 3 concerts.

### MEASURING APPARATUS

### Device hardware and application

An Android application for touch tablets4 was developed
that lets individual audience members indicate the focus of
their attention throughout the performance. They do so by
placing their finger within a designated triangular field on
the screen. The coordinates of the finger positions are sent
continuously via Open Sound Control (OSC) over a
network connection to a host application. The device application
is fully configurable by the researcher and can
run on almost any display size and resolution.
Cases exposing only the triangle interface were produced
by laser cutting cardboard. This design decision was made
to provide a haptic feedback to the users by physically
marking the boundaries of the touch interface. In this way,
it is operable in a more intuitive way and less attention is
drawn to the apparatus as the audience operates the device.

### Host application
The host application allows to operate the collection of
data in the concert situation. This takes place by communicating
with the device apps of all connected tablets, by
naming, starting and stopping the recording as well as defining
the time interval for measurements. The incoming
OSC data sent by the device apps is stored in JSON-files
on the host computer. The data-structure of the JSON-files
is simple, it contains a time stamp in milliseconds for synchronization,
the ID of the sender (the device operated by
the respective subject), the current coordinates of the index
finger in the triangle and the name of the recording:

```
{
  "timestamp": 500,
  "senderId": 1,
  "x": -0.14687499403953552,
  "y": 0.2222018837928772,
  "Recording": "Kilgore"
}
```

Real-time visualizations of the incoming data in the host
app allow observations and monitoring by the researcher
in live situations.
4 Ten Samsung tablets of the type Samsung TAB A where used.

### Analysis application / Knowledge extraction
A third piece of software developed in Java and Processing
allows to load the JSON datasets collected in the preceding
steps and perform calculations and visualizations. What
follows is a concise explanation of the calculations and visualizations
that this software performs.

### Calculations
Activity: Activity is calculated by computing movement
(distance) for each measuring point compared to the last
point in time. This is done for every measuring point for
each subject and also for the whole sample.
Average focus of attention: The mean of the position of
all points (representing all subjects) at a given time is calculated
and displayed in the triangle (see Figure 4). It
shows the average focus of attention at a given time.
Deviation of attention: In a second step the distances
bet-ween the average focus of attention and the focus of
attention of the individual subjects are calculated. They are
displayed by a line in the triangle. From these distances,
the deviation is calculated. Deviation of attention is
intended to be useful for finding sections in performances,
in which the audience’s focus of attention collectively
shifts to certain audiovisual aspects or in which attention
is divergent.

