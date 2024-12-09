package org.sonatype.nexus.upgrade.example;

import org.sonatype.nexus.common.upgrade.Checkpoints;

@Checkpoints(model = "wibble")
public class CheckpointWibble
    extends CheckpointMock
{
}
