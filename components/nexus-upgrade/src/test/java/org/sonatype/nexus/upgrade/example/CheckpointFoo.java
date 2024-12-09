package org.sonatype.nexus.upgrade.example;

import org.sonatype.nexus.common.upgrade.Checkpoints;

@Checkpoints(model = "foo", local = true)
public class CheckpointFoo
    extends CheckpointMock
{
}
