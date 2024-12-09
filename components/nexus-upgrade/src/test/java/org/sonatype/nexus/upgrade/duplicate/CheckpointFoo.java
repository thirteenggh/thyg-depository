package org.sonatype.nexus.upgrade.duplicate;

import org.sonatype.nexus.common.upgrade.Checkpoints;
import org.sonatype.nexus.upgrade.example.CheckpointMock;

@Checkpoints(model = "foo")
public class CheckpointFoo
    extends CheckpointMock
{
}
