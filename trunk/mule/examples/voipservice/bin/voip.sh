#! /bin/sh

# There is no need to call this if you set the MULE_HOME in your environment
if [ -z "$MULE_HOME" ] ; then
  MULE_HOME=../../..
  export MULE_HOME
fi

# Any changes to the files in ../conf will take precedence over those deployed to $MULE_HOME/lib/user
MULE_LIB=../conf
export MULE_LIB

$MULE_HOME/bin/mule -config ../conf/voip-broker-sync-config.xml -main org.mule.samples.voipservice.client.VoipConsumer

