package org.sonatype.nexus.upgrade.example;

import org.sonatype.nexus.common.upgrade.Checkpoints;

@Checkpoints(model = "bar")
public class CheckpointBar
    extends CheckpointMock
{
}
