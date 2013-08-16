/**
 * Copyright 2013 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Eclipse Public License version 1.0, available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package org.jboss.forge.addon.shell.aesh.completion;

import java.util.ArrayList;
import java.util.List;

import org.jboss.aesh.complete.CompleteOperation;
import org.jboss.aesh.complete.Completion;
import org.jboss.forge.addon.shell.ShellImpl;
import org.jboss.forge.addon.shell.aesh.ShellCommand;

/**
 * @author <a href="ggastald@redhat.com">George Gastaldi</a>
 */
public class ForgeCommandCompletion implements Completion
{
   // private static final Logger logger = Logger.getLogger(ForgeCommandCompletion.class.getName());

   private ShellImpl shell;

   public ForgeCommandCompletion(ShellImpl shell)
   {
      this.shell = shell;
   }

   @Override
   public void complete(CompleteOperation completeOperation)
   {
      Iterable<ShellCommand> commands = findMatchingCommands(completeOperation);
      for (ShellCommand cmd : commands)
      {
         String name = cmd.getName();
         completeOperation.addCompletionCandidate(name);
      }
   }

   private Iterable<ShellCommand> findMatchingCommands(CompleteOperation completeOperation)
   {
      List<ShellCommand> result = new ArrayList<ShellCommand>();
      Iterable<ShellCommand> commands = shell.getEnabledShellCommands();

      String[] tokens = completeOperation.getBuffer().split(String.valueOf(completeOperation.getSeparator()));
      if (tokens.length <= 1)
      {
         String token = (tokens.length == 1) ? tokens[0] : null;
         for (ShellCommand cmd : commands)
         {
            if (token == null || cmd.getName().startsWith(token))
               result.add(cmd);
         }
      }
      return result;
   }

}
